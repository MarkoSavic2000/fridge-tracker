package com.fridge.tracker.boot.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(
        packages = "com.fridge.tracker",
        importOptions = {ImportOption.DoNotIncludeTests.class}
)
public class ArchitectureTest {

    @ArchTest
    static final ArchRule applicationModuleShouldOnlyDependOnDomainModule =
            noClasses()
                    .that().resideInAnyPackage("com.fridge.tracker.application..")
                    .should().dependOnClassesThat()
                    .resideInAnyPackage(
                            "com.fridge.tracker.boot..",
                            "com.fridge.tracker.rest..",
                            "com.fridge.tracker.persistence",
                            "com.fridge.tracker.external.interfaces"
                    );

    @ArchTest
    static final ArchRule domainModuleShouldNotDependOnAnyOtherModule =
            noClasses()
                    .that().resideInAnyPackage("com.fridge.tracker.domain..")
                    .should().dependOnClassesThat()
                    .resideInAnyPackage(
                            "com.fridge.tracker.boot..",
                            "com.fridge.tracker.rest..",
                            "com.fridge.tracker.application..",
                            "com.fridge.tracker.persistence",
                            "com.fridge.tracker.external.interfaces"
                    );

    @ArchTest
    static final ArchRule restModuleShouldOnlyDependOnApplicationModule =
            noClasses()
                    .that().resideInAnyPackage("com.fridge.tracker.rest..")
                    .should().dependOnClassesThat()
                    .resideInAnyPackage(
                            "com.fridge.tracker.boot..",
                            "com.fridge.tracker.persistence",
                            "com.fridge.tracker.external.interfaces"
                    );

    @ArchTest
    static final ArchRule persistenceModuleShouldOnlyDependOnApplicationModule =
            noClasses()
                    .that().resideInAnyPackage("com.fridge.tracker.persistence..")
                    .should().dependOnClassesThat()
                    .resideInAnyPackage(
                            "com.fridge.tracker.boot..",
                            "com.fridge.tracker.rest",
                            "com.fridge.tracker.external.interfaces"
                    );

    @ArchTest
    static final ArchRule externalInterfacesModuleShouldOnlyDependOnApplicationModule =
            noClasses()
                    .that().resideInAnyPackage("com.fridge.tracker.external.interfaces..")
                    .should().dependOnClassesThat()
                    .resideInAnyPackage(
                            "com.fridge.tracker.boot..",
                            "com.fridge.tracker.rest",
                            "com.fridge.tracker.persistence"
                    );
}
