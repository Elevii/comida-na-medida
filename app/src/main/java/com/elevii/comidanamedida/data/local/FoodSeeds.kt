package com.elevii.comidanamedida.data.local

object FoodSeeds {
    data class Seed(val uuid: String, val name: String, val cookingIndex: Double)

    fun getAll(): List<Seed> = listOf(
        Seed("2898894f-f785-42f6-9602-cb27dc430e3c", "Carne bovina ", 0.8),
        Seed("561f4a3c-549b-4021-ae58-0bce16fc47a8", "Salmão", 0.83),
        Seed("6edddd7b-d5a0-4390-a33c-46f51b06e207", "Macarrão", 2.2),
        Seed("7079c8b3-6fac-4430-86cf-0cd7f1067bb1", "Ovos", 0.91),
        Seed("7df0b9e8-4e50-47c5-9cd0-0dd9370b5b26", "Peito de frango", 0.75),
        Seed("9b09ecba-0173-4961-a4e7-5e6c8e5b7a3e", "Carne suína", 0.77),
        Seed("9febd69c-09c0-46b2-a727-5559ac432cc2", "Coxa e sobrecoxa de frango", 0.74),
        Seed("bb0026d5-3bcd-4c7d-ba2c-3ea5ece14b7e", "Arroz", 2.5),
        Seed("cb7366fe-ee99-4578-b6d8-5eb57f303527", "Feijão", 2.00),
        Seed("cfff26dd-f9be-49e4-ac8f-c6b04c1d674e", "Peixe branco", 0.87),
        Seed("d128af38-04bf-4cef-9829-65ae826fd00c", "Batata", 1.2),
    )
}