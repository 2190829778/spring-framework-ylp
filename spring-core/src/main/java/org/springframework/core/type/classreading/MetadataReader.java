/*
 * Copyright 2002-2009 the original author or authors.
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

package org.springframework.core.type.classreading;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;

/**
 * 简单的访问类元数据的门面，
 * 通过 ASM {@link org.springframework.asm.ClassReader} 读取。
 *
 * 提供功能以读取类的基本信息和注解，
 * 旨在提供一种方便的方式来访问由 ASM 解析的类元数据。
 *
 * @author Juergen Hoeller
 * @since 2.5
 */
public interface MetadataReader {

    /**
     * 返回类文件的资源引用。
     *
     * @return 表示类文件资源的 Resource 对象
     */
    Resource getResource();

    /**
     * 读取底层类的基本类元数据。
     *
     * 包括类名、父类名、实现的接口等信息，
     * 提供了一种访问类结构信息的方式。
     *
     * @return 包含基本类信息的 ClassMetadata 对象
     */
    ClassMetadata getClassMetadata();

    /**
     * 读取底层类的完整注解元数据，
     * 包括注解方法的元数据。
     *
     * 允许读取类及其方法的所有注解信息，
     * 便于访问类的详细注解数据。
     *
     * @return 包含类的全面注解信息的 AnnotationMetadata 对象
     */
    AnnotationMetadata getAnnotationMetadata();

}

