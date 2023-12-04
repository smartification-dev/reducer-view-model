package dev.smartification.reducers

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.validate

class HiltReducerSymbolProcessor(
    private val environment: SymbolProcessorEnvironment,
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val reducerSymbols = resolver.getSymbolsWithAnnotation("dev.smartification.reducers.HiltReducer")
        reducerSymbols
            .filterIsInstance<KSClassDeclaration>()
            .forEach { reducerClass ->
                val reducerName = reducerClass.simpleName.asString()
                val actionClass = findActionClass(reducerClass)
                val actionPackage = actionClass.packageName.asString()
                val actionParentName = actionClass.parentDeclaration!!.simpleName.asString()
                val destinationPackage = "$actionPackage.di.reducer"
                generateReducerModule(reducerClass, reducerName, destinationPackage, actionClass, actionPackage, actionParentName)
            }
        return reducerSymbols.filterNot { it.validate() }.toList()
    }

    private fun generateReducerModule(
        reducerClass: KSClassDeclaration,
        reducerName: String,
        destinationPackage: String,
        actionClass: KSClassDeclaration,
        actionPackage: String,
        actionParentName: String,
    ) {
        environment.codeGenerator.createNewFile(
            Dependencies(false, reducerClass.containingFile!!),
            destinationPackage,
            "${reducerName}_Module",
        ).also {
            it.write(
                """package $destinationPackage
                            
                   |import dagger.Binds
                   |import dagger.Module
                   |import dagger.hilt.InstallIn
                   |import dagger.hilt.android.components.ViewModelComponent
                   |import dagger.multibindings.IntoMap
                   |import dev.smartification.reducers.Reducer
                   |import ${actionClass.qualifiedName!!.asString()}
                   |import $actionPackage.di.${actionParentName}Key
                   |import ${reducerClass.qualifiedName!!.asString()}
    
                   |@InstallIn(ViewModelComponent::class)
                   |@Module
                   |abstract class ${reducerName}_Module {
    
                   |    @Binds
                   |    @IntoMap
                   |    @${actionClass.parentDeclaration!!.simpleName.asString()}Key(${actionClass.simpleName.asString()}::class)
                   |    abstract fun bind$reducerName(reducer: $reducerName): Reducer<*, *>
                   |}
                """.trimMargin().toByteArray(),
            )
        }
    }

    private fun findActionClass(reducerClass: KSClassDeclaration): KSClassDeclaration {
        val reducerActionType =
            reducerClass.annotations
                .first { it.shortName.asString() == "HiltReducer" }
                .arguments
                .first()
                .value as KSType
        return reducerActionType.declaration as KSClassDeclaration
    }
}
