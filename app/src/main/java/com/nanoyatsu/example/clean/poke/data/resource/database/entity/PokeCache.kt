package com.nanoyatsu.example.clean.poke.data.resource.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nanoyatsu.example.clean.poke.data.resource.network.graphql.pokemon.query.SearchPokemonQuery

@Entity(tableName = "poke_cache")
data class PokeCache(
    @PrimaryKey val id: Int, // ずかん番号
    val name: String, // 名前
    // @ColumnInfo(name = "base_experience") val baseExperience: Int, // 基礎経験値
    val height: Int, // たかさ 10 = 1m
    // @ColumnInfo(name = "is_default")    val isDefault: Boolean, // フォーム違いのやつかどうか
    // val order: Int, // わからん なんらかの順番？ リザードンは７
    val weight: Int, // おもさ 10 = 1kg
    // val species: NamedApiResource, // ずかん情報 たまごグループとかずかんの文とか進化情報とか「ｘｘポケモン」とか（載せたい気持ちはある）
    // val abilities: List<PokemonAbility>, // とくせい PokeAbilityCacheに格納
    // val forms: List<NamedApiResource>, // すがた APIレスポンス的にはURLしか無いので無意味情報
    // val gameIndices: List<VersionGameIndex>, // ゲームタイトル別ずかん番号
    // val heldItems: List<PokemonHeldItem>, // もってるアイテム（？？？）
    // val moves: List<PokemonMove>, // おぼえるわざ 全部ある
    // val stats: List<PokemonStat>, // 種族値 展開して並べた
    @ColumnInfo(name = "stat_h") val statH: Int,
    @ColumnInfo(name = "stat_a") val statA: Int,
    @ColumnInfo(name = "stat_b") val statB: Int,
    @ColumnInfo(name = "stat_c") val statC: Int,
    @ColumnInfo(name = "stat_d") val statD: Int,
    @ColumnInfo(name = "stat_s") val statS: Int,
    // val types: List<PokemonType>, // タイプ PokeTypeCacheに格納
    // val sprites: PokemonSprites // 画像 展開して並べた
    @ColumnInfo(name = "sprite_back_default") val spriteBackDefault: String?,
    @ColumnInfo(name = "sprite_back_shiny") val spriteBackShiny: String?,
    @ColumnInfo(name = "sprite_front_default") val spriteFrontDefault: String?,
    @ColumnInfo(name = "sprite_front_shiny") val spriteFrontShiny: String?,
    @ColumnInfo(name = "sprite_back_female") val spriteBackFemale: String?,
    @ColumnInfo(name = "sprite_back_shiny_female") val spriteBackShinyFemale: String?,
    @ColumnInfo(name = "sprite_front_female") val spriteFrontFemale: String?,
    @ColumnInfo(name = "sprite_front_shiny_female") val spriteFrontShinyFemale: String?
) {
    companion object {
        fun from(data: SearchPokemonQuery.Pokemon?): PokeCache {
            return PokeCache(
                id = data?.number?.toInt() ?: -1,
                name = data?.name ?: "",
                // fixme PokeAPIのGraphQL版で書き直すことにしたので、型はそのままにして種族値とかは適当な値
//                height = data.height,
//                weight = data.weight,
//                statH = data.stats.find { it.id == 1 }?.baseStat ?: 0,
//                statA = data.stats.find { it.id == 2 }?.baseStat ?: 0,
//                statB = data.stats.find { it.id == 3 }?.baseStat ?: 0,
//                statC = data.stats.find { it.id == 4 }?.baseStat ?: 0,
//                statD = data.stats.find { it.id == 5 }?.baseStat ?: 0,
//                statS = data.stats.find { it.id == 6 }?.baseStat ?: 0,
//                spriteBackDefault = data.sprites.backDefault,
//                spriteBackShiny = data.sprites.backShiny,
//                spriteFrontDefault = data.sprites.frontDefault,
//                spriteFrontShiny = data.sprites.frontShiny,
//                spriteBackFemale = data.sprites.backFemale,
//                spriteBackShinyFemale = data.sprites.backShinyFemale,
//                spriteFrontFemale = data.sprites.frontFemale,
//                spriteFrontShinyFemale = data.sprites.frontShinyFemale
                height = 0,
                weight = 0,
                statH = 0,
                statA = 0,
                statB = 0,
                statC = 0,
                statD = 0,
                statS = 0,
                spriteBackDefault = null,
                spriteBackShiny = null,
                spriteFrontDefault = data?.image,
                spriteFrontShiny = null,
                spriteBackFemale = null,
                spriteBackShinyFemale = null,
                spriteFrontFemale = null,
                spriteFrontShinyFemale = null
            )
        }
    }
}