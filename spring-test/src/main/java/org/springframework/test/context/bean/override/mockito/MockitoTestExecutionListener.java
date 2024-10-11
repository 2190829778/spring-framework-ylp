/*
 * Copyright 2002-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.test.context.bean.override.mockito;

import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.mockito.quality.Strictness;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * {@code TestExecutionListener} that enables {@link MockitoBean @MockitoBean}
 * and {@link MockitoSpyBean @MockitoSpyBean} support. Also triggers setup of a
 * {@link MockitoSession} for each test class that uses these annotations (or any
 * annotations in this package).
 *
 * <p>The {@link MockitoSession#setStrictness(Strictness) strictness} of the
 * session defaults to {@link Strictness#STRICT_STUBS}. Use
 * {@link MockitoBeanSettings @MockitoBeanSettings} to specify a different strictness.
 *
 * <p>The automatic reset support for {@code @MockitoBean} and {@code @MockitoSpyBean}
 * is handled by the {@link MockitoResetTestExecutionListener}.
 *
 * @author Simon Baslé
 * @author Phillip Webb
 * @author Andy Wilkinson
 * @author Moritz Halbritter
 * @author Sam Brannen
 * @since 6.2
 * @see MockitoResetTestExecutionListener
 * @see MockitoBean @MockitoBean
 * @see MockitoSpyBean @MockitoSpyBean
 */
public class MockitoTestExecutionListener extends AbstractMockitoTestExecutionListener {

	private static final String MOCKITO_SESSION_ATTRIBUTE_NAME =
			MockitoTestExecutionListener.class.getName() + ".mockitoSession";


	/**
	 * Executes before {@link DependencyInjectionTestExecutionListener}.
	 */
	@Override
	public final int getOrder() {
		return 1950;
	}

	@Override
	public void prepareTestInstance(TestContext testContext) {
		if (mockitoPresent) {
			closeMocks(testContext);
			initMocks(testContext);
		}
	}

	@Override
	public void beforeTestMethod(TestContext testContext) {
		if (mockitoPresent && Boolean.TRUE.equals(
				testContext.getAttribute(DependencyInjectionTestExecutionListener.REINJECT_DEPENDENCIES_ATTRIBUTE))) {
			closeMocks(testContext);
			initMocks(testContext);
		}
	}

	@Override
	public void afterTestMethod(TestContext testContext) {
		if (mockitoPresent) {
			closeMocks(testContext);
		}
	}

	@Override
	public void afterTestClass(TestContext testContext) {
		if (mockitoPresent) {
			closeMocks(testContext);
		}
	}

	private static void initMocks(TestContext testContext) {
		if (hasMockitoAnnotations(testContext)) {
			Class<?> testClass = testContext.getTestClass();
			Object testInstance = testContext.getTestInstance();
			MockitoBeanSettings annotation = AnnotationUtils.findAnnotation(testClass, MockitoBeanSettings.class);
			Strictness strictness = (annotation != null ? annotation.value() : Strictness.STRICT_STUBS);
			testContext.setAttribute(MOCKITO_SESSION_ATTRIBUTE_NAME, initMockitoSession(testInstance, strictness));
		}
	}

	private static MockitoSession initMockitoSession(Object testInstance, Strictness strictness) {
		return Mockito.mockitoSession()
				.initMocks(testInstance)
				.strictness(strictness)
				.startMocking();
	}

	private static void closeMocks(TestContext testContext) {
		if (testContext.getAttribute(MOCKITO_SESSION_ATTRIBUTE_NAME) instanceof MockitoSession session) {
			session.finishMocking();
		}
	}

}
