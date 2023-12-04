package dev.smartification.reducers

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate

class HiltViewModelProcessor(
    private val environment: SymbolProcessorEnvironment,
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val viewModelSymbols = resolver.getSymbolsWithAnnotation("dagger.hilt.android.lifecycle.HiltViewModel")
        viewModelSymbols
            .filterIsInstance<KSClassDeclaration>()
            .forEach { viewModelClass ->
                val viewModelPackage = viewModelClass.packageName.asString()
                val viewModelName = viewModelClass.simpleName.asString()
                val featureName = viewModelName.removeSuffix("ViewModel")
                val destinationPackage = "$viewModelPackage.di"
                generateViewModelModule(viewModelClass, destinationPackage, viewModelName, viewModelPackage, featureName)
                generateActionKey(viewModelClass, destinationPackage, featureName, viewModelPackage)
            }
        return viewModelSymbols.filterNot { it.validate() }.toList()
    }

    private fun generateActionKey(
        viewModelClass: KSClassDeclaration,
        destinationPackage: String,
        featureName: String,
        viewModelPackage: String
    ) {
        environment.codeGenerator.createNewFile(
            Dependencies(false, viewModelClass.containingFile!!),
            destinationPackage,
            "${featureName}ActionKey",
        ).also {
            it.write(
                """package $destinationPackage                           

                   |import dagger.MapKey
                   |import $viewModelPackage.${featureName}Action
                   |import kotlin.reflect.KClass
                                
                   |@MapKey
                   |annotation class ${featureName}ActionKey(val value: KClass<out ${featureName}Action>)
                """.trimMargin().toByteArray(),
            )
        }
    }

    private fun generateViewModelModule(
        viewModelClass: KSClassDeclaration,
        destinationPackage: String,
        viewModelName: String,
        viewModelPackage: String,
        featureName: String
    ) {
        environment.codeGenerator.createNewFile(
            Dependencies(false, viewModelClass.containingFile!!),
            destinationPackage,
            "${viewModelName}_Module",
        ).also {
            it.write(
                """package $destinationPackage
                               
                   |import dev.smartification.reducers.Reducer
                   |import dagger.Module
                   |import dagger.Provides
                   |import dagger.hilt.InstallIn
                   |import dagger.hilt.android.components.ViewModelComponent
                   |import dagger.multibindings.Multibinds
                   |import $viewModelPackage.${featureName}Action
                   |import $viewModelPackage.${featureName}State
                                
                   |@InstallIn(ViewModelComponent::class)
                   |@Module
                   |abstract class ${viewModelName}_Module {
                   |    @Multibinds
                   |    abstract fun provide${featureName}Reducers(): Map<Class<out ${featureName}Action>, Reducer<*, *>>
                   |}
                """.trimMargin().toByteArray(),
            )
        }
    }
}
