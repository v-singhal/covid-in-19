package com.vbstudio.annotationprocessor.processors;

import com.vbstudio.annotations.DaggerActivity;
import com.vbstudio.annotations.DaggerFragment;
import com.samskivert.mustache.Mustache;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.JavaFileObject;


@SupportedAnnotationTypes({"com.vbstudio.annotations.DaggerActivity", "com.vbstudio.annotations.DaggerFragment"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class DaggerBuildersAnnotationProcessor extends AbstractProcessor {

    private final String ACTIVITY_SCOPE = "ActivityScope";
    private final String FRAGMENT_SCOPE = "FragmentScope";
    private final String BUILDER_TEMPLATE = "/templates/dagger_template.txt";

    private Set<String> imports;
    private List<BuilderItem> builders;

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        imports = new HashSet<>();
        builders = new ArrayList<>();

        processAnnotation(roundEnvironment, DaggerActivity.class, ACTIVITY_SCOPE);
        processAnnotation(roundEnvironment, DaggerFragment.class, FRAGMENT_SCOPE);

        writeBuildersModuleSourceFile();

        return true;
    }

    private void processAnnotation(RoundEnvironment roundEnvironment, Class<? extends Annotation> annotation, String scope){
        Collection<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(annotation);
        List<TypeElement> typeElements = new ArrayList<>(ElementFilter.typesIn(elements));
        for (TypeElement typeElement : typeElements) {
            processType(typeElement, annotation, scope);
        }
    }

    private void processType(TypeElement typeElement, Class<? extends Annotation> annotation, String scope) {
        imports.add(getImport(typeElement));
        builders.add(new BuilderItem(getClassName(typeElement), getModules(typeElement, annotation), scope));
    }


    private void writeBuildersModuleSourceFile() {
        final StringBuilder builder = new StringBuilder();
        String templateString = readBuilderModuleTemplate();

        final List<String> toImport = new ArrayList<>(this.imports);
        final List<BuilderItem> toBuildersName = new ArrayList<>(this.builders);

        boolean isAndroidAnnotationsPass = true;

        for (BuilderItem item: builders){
            isAndroidAnnotationsPass &= item.name.contains("_");
        }

        final String toModuleName = getModuleName(isAndroidAnnotationsPass);

        String string = Mustache.compiler().compile(templateString).execute(new Object() {
            Object imports = toImport;
            Object builders = toBuildersName;
            Object moduleName = toModuleName;
        });

        builder.append(string);

        try {
            JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(getActivityModuleFilePath(toModuleName));
            Writer writer = sourceFile.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException ignored) {

        }
    }

    private String readBuilderModuleTemplate() {
        String template;
        InputStream inputStream = this.getClass().getResourceAsStream(BUILDER_TEMPLATE);
        if (inputStream == null) return null;
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        template = scanner.hasNext() ? scanner.next() : "";
        return template;
    }

    private List<String> getModules(TypeElement typeElement, Class<? extends Annotation> annotation){

        List<String> moduleNames = new ArrayList<>();

        if (annotation.equals(DaggerActivity.class)) {
            try {
                typeElement.getAnnotation(DaggerActivity.class).modules();
            } catch (MirroredTypesException e) {
                List<? extends TypeMirror> typeMirrors = e.getTypeMirrors();
                for (TypeMirror typeMirror : typeMirrors) {
                    String name = typeMirror.toString();
                    String[] tokens = name.split("\\.");
                    if (tokens.length != 0) {
                        imports.add(name);
                        moduleNames.add(tokens[tokens.length - 1]);
                    }
                }
            }
        } else if (annotation.equals(DaggerFragment.class)) {
            try {
                typeElement.getAnnotation(DaggerFragment.class).modules();
            } catch (MirroredTypesException e) {
                List<? extends TypeMirror> typeMirrors = e.getTypeMirrors();
                for (TypeMirror typeMirror : typeMirrors) {
                    String name = typeMirror.toString();
                    String[] tokens = name.split("\\.");
                    if (tokens.length != 0) {
                        imports.add(name);
                        moduleNames.add(tokens[tokens.length - 1]);
                    }
                }
            }
        }

        return moduleNames;
    }

    private String getImport(TypeElement typeElement) {
        return typeElement.asType().toString();
    }

    private String getClassName(TypeElement typeElement) {
        return typeElement.getSimpleName().toString();
    }

    private String getActivityModuleFilePath(String moduleName) {
        return "com.vbstudio.covid19.injector.modules." + moduleName;
    }

    private String getModuleName(boolean isAndroidAnnotationsPass){
        return "BuildersModule" + (isAndroidAnnotationsPass ? "_" : "");
    }

    private class BuilderItem {
        private String name;
        private List<String> modules;
        private String scope;

        public BuilderItem(String name, List<String> modules, String scope) {
            this.name = name;
            this.modules = modules;
            this.scope = scope;
        }
    }
}
