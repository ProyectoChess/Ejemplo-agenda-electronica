package ejercitacion.Agenda
import kotlin.system.exitProcess

class Contacto(var nombre: String, var numeroDeTelefono: Number?) {}

class Agenda() {
    var listaDeContactos: MutableList<Contacto> = mutableListOf()
    val formatosDeNumeroDeTelefonoAceptados: List<Int> = listOf(9, 10, 11, 12)

    init { insertarContactosDePrueba() }

    fun mostrarMenuPrincipal() {
        val opciones: List<Int> = listOf(1, 2)

        var input: Int? = null

        while (input !in opciones) {
            println(
                """
                1) Mostrar menu de acciones
                2) Cerrar agenda
                """.trimIndent()
            )
            input = this.pedirEntradaNumericaPorConsola()

            if (input !in opciones) {
                println("Por favor, ingresa una opción válida.")
            }
        }

        when (input) {
            1 -> this.iniciarAgenda()
            2 -> this.cerrarAgenda()
        }
        println("---------------------------------------------")
    }

    fun iniciarAgenda() {

        val input: Int? = mostrarMenuDeAcciones()
        println("----------------------------------")

        when (input) {
            1 -> this.mostrarContactos()
            2 -> this.agregarContacto()
            3 -> this.eliminarContacto()
            4 -> this.editarContacto()
            5 -> this.ordenarContactos()
        }
        this.mostrarMenuPrincipal()
    }

    fun cerrarAgenda() {
        exitProcess(0)
    }

    fun mostrarMenuDeAcciones(): Int? {
        val opciones: List<Int> = listOf(1, 2, 3, 4, 5)

        var input: Int? = null

        while (input !in opciones) {
            println(
                """
                    1) Mostrar contactos
                    2) Agregar contacto
                    3) Eliminar contacto
                    4) Editar contacto
                    5) Ordenar contactos
                    """.trimIndent()
            )
            input = this.pedirEntradaNumericaPorConsola()

            if (input !in opciones) {
                println("Por favor, ingresá una opción válida.")
            }
        }
        return input
    }

    fun mostrarContactos() {
        println("Estos son tus contactos: ")
        for (contacto in this.listaDeContactos){
            println("${contacto.nombre}, ${contacto.numeroDeTelefono}")
        }
    }

    fun agregarContacto(contacto: Contacto? = null) {

        if (contacto == null) {
            println("Ingresa el nombre del contacto: ")
            var inputNombre: String = readLine() ?: ""
            println("Ingresa el numero de telefono del contacto: ")
            var inputNumeroDeTelefono: Number? = readLine()?.toIntOrNull()
            listaDeContactos.add(Contacto(inputNombre, inputNumeroDeTelefono))
        }
        else{
            listaDeContactos.add(contacto)
        }
        println("Contacto agregado -> ${contacto?.nombre} ")

    }

    fun eliminarContacto() {
        println("ingresa el nombre del usuario que queres borrar: ")
        val input = readlnOrNull()
        this.listaDeContactos.removeIf { it.nombre == input || it.nombre.lowercase() == input }
    }

    fun editarContacto() {
        println("ingresa el nombre del usuario que queres modificar: ")
        val inputNombre: String = readLine() ?: "NN"
        println("ingresa el nuevo nombre: ")
        val inputNuevoNombre: String = readLine() ?: "NN"
        println("ingresa el nuevo numero de telefono: ")
        val inputNuevoTelefono: Number? = readLine()?.toIntOrNull()

        val copiaListaDeContactos = this.listaDeContactos.toMutableList()

        for (contacto in copiaListaDeContactos) {
            if (contacto.nombre == inputNombre) {
                contacto.nombre = inputNuevoNombre
                contacto.numeroDeTelefono = inputNuevoTelefono
            }
        }
    }

    fun ordenarContactos() {
        println(
            """
            Ingresa el criterio de ordenamiento:
            1 para ordenar por nombre
            2 para ordenar por numero de telefono
            """.trimIndent()
        )

        var inputCriterioDeOrdenamiento: Int? = this.pedirEntradaNumericaPorConsola()
        var comparador: Comparator<Contacto> = compareBy {it.nombre.lowercase()}

        if (inputCriterioDeOrdenamiento == 1) {
            /*
             no es necesario volver a declarar la variable con val/var
             porque la variable comparador quedara "shadowed"
             esto ocurre porque un condicional define un ambito, y la variable declarada queda 'atrapada' en ese ambito
            */
            comparador = compareBy {it.nombre.lowercase()}
        }
        else if (inputCriterioDeOrdenamiento == 2) {
            comparador = compareBy {it.numeroDeTelefono as Int}
        }
        else {
            println("No ingresaste un criterio de ordenamiento: ")
        }


        val listaDeContactosOrdenada: MutableList<Contacto> = this.listaDeContactos.sortedWith(comparador).toMutableList()
        this.listaDeContactos = listaDeContactosOrdenada
    }

    fun insertarContactosDePrueba() {
        this.agregarContacto(Contacto("Juan", 1212323569))
        this.agregarContacto(Contacto("Sofia", 1323546578))
        this.agregarContacto(Contacto("Pedro", 1143243253))
        this.agregarContacto(Contacto("Alan", 1223232455))
        this.agregarContacto(Contacto("Franco", 15553434560))
    }

    fun pedirEntradaNumericaPorConsola(): Int {
        var input: Int? = readLine()?.toIntOrNull()

        while(input == null) {
            input = readLine()?.toIntOrNull()
        }
        return input
    }

    fun pedirEntradaDeNumeroDeTelefono(): Number {
        var input: Number? = readLine()?.toIntOrNull()

        while(input == null || input.toString().length !in this.formatosDeNumeroDeTelefonoAceptados) {
            input = readLine()?.toIntOrNull()
        }
        return input
    }
}