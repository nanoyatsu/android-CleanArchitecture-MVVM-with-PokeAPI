package com.nanoyatsu.example.clean.poke.domain.poke

import com.nanoyatsu.example.clean.poke.data.database.relation.PokeCacheAbilityWithName
import com.nanoyatsu.example.clean.poke.data.database.relation.PokeCacheTypeWithName
import com.nanoyatsu.example.clean.poke.data.database.relation.PokeCacheWithTypeAndAbility

data class PokeDetail(
    val number: Int, // ずかん番号
    val name: String, // 名前
    // val species: NamedApiResource, // ずかん情報 たまごグループとかずかんの文とか進化情報とか「ｘｘポケモン」とか（載せたい気持ちはある）
    val height: Double, // たかさ 1 = 1m
    val weight: Double, // おもさ 1 = 1kg
    val abilities: List<PokeAbility>,
    val types: List<PokeType>, // タイプ PokeTypeCacheに格納
    val statH: Int,
    val statA: Int,
    val statB: Int,
    val statC: Int,
    val statD: Int,
    val statS: Int,
    val spriteBackDefault: String?,
    val spriteBackShiny: String?,
    val spriteFrontDefault: String?,
    val spriteFrontShiny: String?,
    val spriteBackFemale: String?,
    val spriteBackShinyFemale: String?,
    val spriteFrontFemale: String?,
    val spriteFrontShinyFemale: String?
) {
    companion object {
        fun from(data: PokeCacheWithTypeAndAbility): PokeDetail {
            return PokeDetail(
                number = data.poke.id,
                name = data.poke.name,
                height = data.poke.height / 10.0,
                weight = data.poke.weight / 10.0,
                abilities = data.abilities.map { PokeAbility.from(it) },
                types = data.types.map { PokeType.from(it) },
                statH = data.poke.statH,
                statA = data.poke.statA,
                statB = data.poke.statB,
                statC = data.poke.statC,
                statD = data.poke.statD,
                statS = data.poke.statS,
                spriteBackDefault = data.poke.spriteBackDefault,
                spriteBackShiny = data.poke.spriteBackShiny,
                spriteFrontDefault = data.poke.spriteFrontDefault,
                spriteFrontShiny = data.poke.spriteFrontShiny,
                spriteBackFemale = data.poke.spriteBackFemale,
                spriteBackShinyFemale = data.poke.spriteBackShinyFemale,
                spriteFrontFemale = data.poke.spriteFrontFemale,
                spriteFrontShinyFemale = data.poke.spriteFrontShinyFemale
            )
        }
    }
}

data class PokeType(
    val name: String,
    val slot: Int
) {
    companion object {
        fun from(data: PokeCacheTypeWithName): PokeType {
            return PokeType(data.type.name, data.pokeType.slot)
        }
    }
}

data class PokeAbility(
    val name: String,
    val slot: Int,
    val isHidden: Boolean
) {
    companion object {
        fun from(data: PokeCacheAbilityWithName): PokeAbility {
            return PokeAbility(data.ability.name, data.pokeAbility.slot, data.pokeAbility.isHidden)
        }
    }
}