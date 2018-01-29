/**
 * MIT License
 *
 * Copyright (c) 2016 - 2018 Silvio Wangler (silvio.wangler@gmail.com)
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
package ch.silviowangler.gradle.restapi;

import com.squareup.javapoet.ClassName;

import javax.annotation.Generated;

/**
 * Created by Silvio Wangler on 26/01/16.
 */
public enum PluginTypes {

    RESTAPI_IDTYPE(ClassName.get("ch.silviowangler.rest.types", "IdType")),
    RESTAPI_FILTERMODEL(ClassName.get("ch.silviowangler.rest.model", "FilterModel")),
    RESTAPI_JWT_ANNOTATION(ClassName.get("ch.silviowangler.rest.security", "SecurityEnabled")),
    RESTAPI_CACHING_ANNOTATION(ClassName.get("ch.silviowangler.rest.cache", "CacheSetting")),
    JAX_RS_RESPONSE(ClassName.get("javax.ws.rs.core", "Response")),
    JAX_RS_OPTIONS_VERB(ClassName.get("javax.ws.rs", "OPTIONS")),
    JAX_RS_GET_VERB(ClassName.get("javax.ws.rs", "GET")),
    JAX_RS_POST_VERB(ClassName.get("javax.ws.rs", "POST")),
    JAX_RS_PUT_VERB(ClassName.get("javax.ws.rs", "PUT")),
    JAX_RS_DELETE_VERB(ClassName.get("javax.ws.rs", "DELETE")),
    JAX_RS_PRODUCES(ClassName.get("javax.ws.rs", "Produces")),
    JAX_RS_CONSUMES(ClassName.get("javax.ws.rs", "Consumes")),
    JAX_RS_PATH(ClassName.get("javax.ws.rs", "Path")),
    JAX_RS_FORM_PARAM(ClassName.get("javax.ws.rs", "FormParam")),
    JAX_RS_PATH_PARAM(ClassName.get("javax.ws.rs", "PathParam")),
    JAX_RS_QUERY_PARAM(ClassName.get("javax.ws.rs", "QueryParam")),
    JAX_RS_CONTEXT(ClassName.get("javax.ws.rs.core", "Context")),
    JAVAX_VALIDATION_SIZE(ClassName.get("javax.validation.constraints", "Size")),
    JAVAX_VALIDATION_NOT_NULL(ClassName.get("javax.validation.constraints", "NotNull")),
    JAVAX_VALIDATION_VALID(ClassName.get("javax.validation", "Valid")),
    JAVAX_GENERATED(ClassName.get(Generated.class)),
    JAVA_OVERRIDE(ClassName.get(Override.class)),
    PLUGIN_NOT_YET_IMPLEMENTED_EXCEPTION(ClassName.get("ch.silviowangler.rest", "NotYetImplementedException")),
    SPRING_REQUEST_MAPPING(ClassName.get("org.springframework.web.bind.annotation", "RequestMapping")),
    SPRING_REQUEST_PARAM(ClassName.get("org.springframework.web.bind.annotation", "RequestParam")),
    SPRING_PATH_VARIABLE(ClassName.get("org.springframework.web.bind.annotation", "PathVariable")),
    SPRING_REQUEST_METHOD(ClassName.get("org.springframework.web.bind.annotation", "RequestMethod")),
    SPRING_REST_CONTROLLER(ClassName.get("org.springframework.web.bind.annotation", "RestController")),
    SPRING_RESPONSE_ENTITY(ClassName.get("org.springframework.http", "ResponseEntity")),
    SPRING_HTTP_STATUS(ClassName.get("org.springframework.http", "HttpStatus")),
    SPRING_HTTP_MEDIA_TYPE(ClassName.get("org.springframework.http", "MediaType")),
    SPRING_RESPONSE_BODY(ClassName.get("org.springframework.web.bind.annotation", "ResponseBody"));


    private ClassName className;

    PluginTypes(ClassName className) {
        this.className = className;
    }

    public ClassName getClassName() {
        return this.className;
    }

}