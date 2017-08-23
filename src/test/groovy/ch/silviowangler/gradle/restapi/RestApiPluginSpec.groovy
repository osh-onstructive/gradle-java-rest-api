/*
 * MIT License
 *
 * Copyright (c) 2017 - 2017 Silvio Wangler (silvio.wangler@gmail.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package ch.silviowangler.gradle.restapi

import com.squareup.javapoet.ClassName
import groovy.io.FileType
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

import java.nio.charset.Charset

@Stepwise
class RestApiPluginSpec extends Specification {

    Project project = ProjectBuilder.builder().build()

    @Rule
    TemporaryFolder temporaryFolder

    @Shared
    def customFieldModelMapping = { resource, field ->

        if (resource.general.description == 'Natürliche Person') {

            if (field.name == 'leistungsabrechnungspositionen') {
                return ClassName.get(BigDecimal)
            } else if (field.name == 'sprache') {
                return ClassName.get('java.util', 'Locale')
            }
        }
        throw new RuntimeException("Mapping no defined for ${field.name} of resource '${resource.general.description}'")
    }

    void setup() {
        project.apply plugin: 'java'
        project.apply plugin: RestApiPlugin.PLUGIN_ID
    }

    void "Das Plugin stellt einen Task zur Verfügung"() {

        expect:
        project.tasks.generateRestArtefacts instanceof GenerateRestApiTask

        and:
        project.extensions.restApi != null
    }

    void "Das Plugin generiert Java 8 kompatiblen Source Code"() {

        given:
        project.restApi.generatorOutput = temporaryFolder.getRoot()
        project.restApi.generatorImplOutput = temporaryFolder.getRoot()
        project.restApi.optionsSource = new File("${new File('').absolutePath}/src/test/resources/specs/demo")
        project.restApi.packageName = 'org.acme.rest'
        project.restApi.generateDateAttribute = false
        project.restApi.objectResourceModelMapping = customFieldModelMapping

        and:
        GenerateRestApiTask task = project.tasks.generateRestArtefacts

        when:
        task.exec()

        and:
        def javaFiles = []
        temporaryFolder.getRoot().eachFileRecurse(FileType.FILES, {
            if (it.name.endsWith('.java')) javaFiles << it
        })

        then:
        new File(temporaryFolder.getRoot(), 'org/acme/rest').exists()

        and:
        javaFiles.size() == 13

        and:
        javaFiles.collect {
            it.parent == new File(temporaryFolder.getRoot(), 'org/acme/rest')
        }.size() == javaFiles.size()

        and: 'resource validieren'
        assertJavaFile('org.acme.rest.v1', 'PartnerResource')
        assertJavaFile('org.acme.rest.v1', 'PartnerResourceImpl')
        assertJavaFile('org.acme.rest.v1', 'PartnerGetResourceModel')
        assertJavaFile('org.acme.rest.v1', 'PartnerPutResourceModel')
        assertJavaFile('org.acme.rest.v1', 'PartnerPostResourceModel')

        when:
        CleanRestApiTask cleanTask = project.tasks.cleanRestArtefacts

        and:
        cleanTask.cleanUp()

        and:
        javaFiles.clear()
        temporaryFolder.getRoot().eachFileRecurse(FileType.FILES, {
            if (it.name.endsWith('.java')) javaFiles << it
        })

        then:
        javaFiles.isEmpty()
    }

    void "Typ-Definitionen in root.json werden berücksichtigt"() {

        given:
        project.restApi.generatorOutput = temporaryFolder.getRoot()
        project.restApi.generatorImplOutput = temporaryFolder.getRoot()
        project.restApi.optionsSource = new File("${new File('').absolutePath}/src/test/resources/specs/v1/")
        project.restApi.packageName = 'org.acme.rest'
        project.restApi.generateDateAttribute = false
        project.restApi.objectResourceModelMapping = customFieldModelMapping
        project.restApi.enableSecurity = true
        project.restApi.responseEncoding = Charset.forName('UTF-8')

        and:
        GenerateRestApiTask task = project.tasks.generateRestArtefacts

        when:
        task.exec()

        and:
        def javaFiles = []
        temporaryFolder.getRoot().eachFileRecurse(FileType.FILES, {
            if (it.name.endsWith('.java')) javaFiles << it
        })

        then:
        new File(temporaryFolder.getRoot(), 'org/acme/rest').exists()

        and:
        javaFiles.size() == 14

        and:
        javaFiles.collect {
            it.parent == new File(temporaryFolder.getRoot(), 'org/acme/rest')
        }.size() == javaFiles.size()

        and: 'resource validieren'
        assertJavaFile('org.acme.rest.v1', 'CoordinatesType', 'land')
        assertJavaFile('org.acme.rest.v1', 'LandGetResourceModel', 'land')
        assertJavaFile('org.acme.rest.v1', 'LandPutResourceModel', 'land')
        assertJavaFile('org.acme.rest.v1', 'LandPostResourceModel', 'land')
        assertJavaFile('org.acme.rest.v1', 'LandResource', 'land')
        assertJavaFile('org.acme.rest.v1', 'LandResourceImpl', 'land')

        assertJavaFile('org.acme.rest.v1.laender', 'OrtResource', 'land')
        assertJavaFile('org.acme.rest.v1.laender', 'OrtResourceImpl', 'land')
        assertJavaFile('org.acme.rest.v1.laender', 'OrtGetResourceModel', 'land')
        assertJavaFile('org.acme.rest.v1.laender', 'OrtPutResourceModel', 'land')
        assertJavaFile('org.acme.rest.v1.laender', 'OrtPostResourceModel', 'land')

        assertJavaFile('org.acme.rest.v1', 'RootResource', 'land')
        assertJavaFile('org.acme.rest.v1', 'RootResourceImpl', 'land')
        assertJavaFile('org.acme.rest.v1', 'RootGetResourceModel', 'land')

        when:
        CleanRestApiTask cleanTask = project.tasks.cleanRestArtefacts

        and:
        cleanTask.cleanUp()

        and:
        javaFiles.clear()
        temporaryFolder.getRoot().eachFileRecurse(FileType.FILES, {
            if (it.name.endsWith('.java')) javaFiles << it
        })

        then:
        javaFiles.isEmpty()
    }

    void "Ein nicht spezifiziertes Verb kann explizit ausgelassen werden"() {

        given:
        project.restApi.generatorOutput = temporaryFolder.getRoot()
        project.restApi.generatorImplOutput = temporaryFolder.getRoot()
        project.restApi.optionsSource = new File("${new File('').absolutePath}/src/test/resources/specs/root/")
        project.restApi.packageName = 'org.acme.rest'
        project.restApi.generateDateAttribute = false
        project.restApi.objectResourceModelMapping = customFieldModelMapping
        project.restApi.responseEncoding = Charset.forName('UTF-8')

        and:
        GenerateRestApiTask task = project.tasks.generateRestArtefacts

        when:
        task.exec()

        and:
        def javaFiles = []
        temporaryFolder.getRoot().eachFileRecurse(FileType.FILES, {
            if (it.name.endsWith('.java')) javaFiles << it
        })

        then:
        new File(temporaryFolder.getRoot(), 'org/acme/rest').exists()

        and:
        javaFiles.size() == 3

        and:
        javaFiles.collect {
            it.parent == new File(temporaryFolder.getRoot(), 'org/acme/rest')
        }.size() == javaFiles.size()

        and: 'resource validieren'
        assertJavaFile('org.acme.rest.v1', 'RootGetResourceModel', 'root')
        assertJavaFile('org.acme.rest.v1', 'RootResource', 'root')
        assertJavaFile('org.acme.rest.v1', 'RootResourceImpl', 'root')

        when:
        CleanRestApiTask cleanTask = project.tasks.cleanRestArtefacts

        and:
        cleanTask.cleanUp()

        and:
        javaFiles.clear()
        temporaryFolder.getRoot().eachFileRecurse(FileType.FILES, {
            if (it.name.endsWith('.java')) javaFiles << it
        })

        then:
        javaFiles.isEmpty()
    }

    void "Das Plugin generiert auch read only Ressourcen mit nur einem Collection GET"() {

        given:
        project.restApi.generatorOutput = temporaryFolder.getRoot()
        project.restApi.generatorImplOutput = temporaryFolder.getRoot()
        project.restApi.optionsSource = new File("${new File('').absolutePath}/src/test/resources/specs/collectionOnly")
        project.restApi.packageName = 'org.acme.rest'
        project.restApi.generateDateAttribute = false
        project.restApi.objectResourceModelMapping = customFieldModelMapping

        and:
        GenerateRestApiTask task = project.tasks.generateRestArtefacts

        when:
        task.exec()

        and:
        def javaFiles = []
        temporaryFolder.getRoot().eachFileRecurse(FileType.FILES, {
            if (it.name.endsWith('.java')) javaFiles << it
        })

        then:
        new File(temporaryFolder.getRoot(), 'org/acme/rest').exists()

        and:
        javaFiles.size() == 3

        and:
        javaFiles.collect {
            it.parent == new File(temporaryFolder.getRoot(), 'org/acme/rest')
        }.size() == javaFiles.size()

        and: 'resource validieren'
        assertJavaFile('org.acme.rest.v1', 'PartnersearchResource', 'collectionGet')
        assertJavaFile('org.acme.rest.v1', 'PartnersearchResourceImpl', 'collectionGet')
        assertJavaFile('org.acme.rest.v1', 'PartnersearchGetResourceModel', 'collectionGet')

        when:
        CleanRestApiTask cleanTask = project.tasks.cleanRestArtefacts

        and:
        cleanTask.cleanUp()

        and:
        javaFiles.clear()
        temporaryFolder.getRoot().eachFileRecurse(FileType.FILES, {
            if (it.name.endsWith('.java')) javaFiles << it
        })

        then:
        javaFiles.isEmpty()
    }

    private void assertJavaFile(String packageName, String className) {
        assertJavaFile(packageName, className, 'default')
    }

    private void assertJavaFile(String packageName, String className, String testName) {
        final String ENCODING = 'UTF-8'
        File expectedJavaFile = new File(temporaryFolder.getRoot().absolutePath + '/' + packageName.replaceAll('\\.', '/'), "${className}.java")
        File actualJavaFile = new File(getClass().getResource("/javaOutput/${testName}/${className}.java.txt").file)

        final String expectedJavaSourceCode = expectedJavaFile.getText(ENCODING)
        final String actualJavaSourceCode = actualJavaFile.getText(ENCODING)

        assert expectedJavaSourceCode == actualJavaSourceCode
    }
}