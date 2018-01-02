/*
 *
 *  Copyright 2015-2018 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */
package springfox.documentation.schema

import com.fasterxml.classmate.ResolvedType
import com.google.common.base.Optional
import com.google.common.collect.ImmutableSet
import springfox.documentation.schema.mixins.ModelProviderSupport
import springfox.documentation.schema.mixins.TypesForTestingSupport

import static springfox.documentation.spi.schema.contexts.ModelContext.*

import java.util.Set

@Mixin([TypesForTestingSupport, ModelProviderSupport, AlternateTypesSupport])
class BeanWithFactoryMethodSpec extends SchemaSpecification {
  def "Type with bean properties in the constructor" () {
    given:
      def sut = defaultModelProvider()
      def typeToTest = resolver.resolve(typeWithConstructorProperties())
      def reqContext = inputParam(
          "group",
          typeToTest,
          Optional.absent(),
          new HashSet<>(),
          documentationType,
          new TypeNameIndexingAdapter(),
          alternateTypeProvider(),
          new DefaultGenericTypeNamingStrategy(),
          ImmutableSet.builder().build())
      def resContext = returnValue(
          "group",
          typeToTest,
          Optional.absent(),
          documentationType,
          new TypeNameIndexingAdapter(),
          alternateTypeProvider(),
          new DefaultGenericTypeNamingStrategy(),
          ImmutableSet.builder().build())

    when:
      def models = [sut.modelFor(reqContext).get(), sut.modelFor(resContext).get()]

    then:
      models.each {
        it.properties.size() == 2
        it.properties.containsKey(fieldName)
        it.properties."$fieldName".description == description
        it.properties."$fieldName".required == isRequired
        it.properties."$fieldName".type.erasedType == type
        it.properties."$fieldName".qualifiedType == qualifiedTypeName
        it.properties."$fieldName".allowableValues == allowableValues
        true
      }

    where:
    fieldName || description  | isRequired  | type    | qualifiedTypeName   | allowableValues
    "foo"     || null         | true        | String  | "java.lang.String"  | null
    "bar"     || null         | true        | Integer | "java.lang.Integer" | null
  }

  def "Type with delegated constructor (factory method)" () {
    given:
      def sut = defaultModelProvider()
      def typeToTest = resolver.resolve(typeWithDelegatedConstructor())
      def reqContext = inputParam(
          "group",
          typeToTest,
          Optional.absent(),
          new HashSet<>(),
          documentationType,
          new TypeNameIndexingAdapter(),
          alternateTypeProvider(),
          new DefaultGenericTypeNamingStrategy(),
          ImmutableSet.builder().build())
      def resContext = returnValue(
          "group",
          typeToTest,
          Optional.absent(),
          documentationType,
          new TypeNameIndexingAdapter(),
          alternateTypeProvider(),
          new DefaultGenericTypeNamingStrategy(),
          ImmutableSet.builder().build())

    when:
      def models = [sut.modelFor(reqContext).get(), sut.modelFor(resContext).get()]

    then:
      models.each {
        it.properties.size() == 2
        it.properties.containsKey(fieldName)
        it.properties."$fieldName".description == description
        it.properties."$fieldName".required == isRequired
        it.properties."$fieldName".type.erasedType == type
        it.properties."$fieldName".qualifiedType == qualifiedTypeName
        it.properties."$fieldName".allowableValues == allowableValues
        true
      }

    where:
    fieldName || description  | isRequired  | type    | qualifiedTypeName   | allowableValues
    "foo"     || null         | true        | String  | "java.lang.String"  | null
    "bar"     || null         | true        | Integer | "java.lang.Integer" | null
  }

  def "Type with @JsonCreator marked constructor" () {
    given:
      def sut = defaultModelProvider()
      def typeToTest = resolver.resolve(typeWithDelegatedConstructor())
      def reqContext = inputParam(
          "group",
          typeToTest,
          Optional.absent(),
          new HashSet<>(),
          documentationType,
          new TypeNameIndexingAdapter(),
          alternateTypeProvider(),
          new DefaultGenericTypeNamingStrategy(),
          ImmutableSet.builder().build())
      def resContext = returnValue(
          "group",
          typeToTest,
          Optional.absent(),
          documentationType,
          new TypeNameIndexingAdapter(),
          alternateTypeProvider(),
          new DefaultGenericTypeNamingStrategy(),
          ImmutableSet.builder().build())

    when:
      def models = [sut.modelFor(reqContext).get(), sut.modelFor(resContext).get()]

    then:
      models.each {
        it.properties.size() == 2
        it.properties.containsKey(fieldName)
        it.properties."$fieldName".description == description
        it.properties."$fieldName".required == isRequired
        it.properties."$fieldName".type.erasedType == type
        it.properties."$fieldName".qualifiedType == qualifiedTypeName
        it.properties."$fieldName".allowableValues == allowableValues
        true
      }

    where:
    fieldName || description  | isRequired  | type    | qualifiedTypeName   | allowableValues
    "foo"     || null         | true        | String  | "java.lang.String"  | null
    "bar"     || null         | true        | Integer | "java.lang.Integer" | null
  }
}

