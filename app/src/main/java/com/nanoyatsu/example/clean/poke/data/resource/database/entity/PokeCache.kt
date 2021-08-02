package com.nanoyatsu.example.clean.poke.data.resource.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nanoyatsu.example.clean.poke.data.resource.network.graphql.pokemon.query.SearchPokemonQuery
import com.nanoyatsu.example.clean.poke.data.resource.network.pokeApi.query.FetchPokeDetailQuery

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
        fun from(data: FetchPokeDetailQuery.Pokemon_v2_pokemon): PokeCache {
            return PokeCache(
                id = data.id,
                name = data.name,
                height = data.height ?: 0,
                weight = data.weight ?: 0,
                // fixme use stats.id master
                statH = data.pokemon_v2_pokemonstats.find { it.stat_id == 1 }?.base_stat ?: 0,
                statA = data.pokemon_v2_pokemonstats.find { it.stat_id == 2 }?.base_stat ?: 0,
                statB = data.pokemon_v2_pokemonstats.find { it.stat_id == 3 }?.base_stat ?: 0,
                statC = data.pokemon_v2_pokemonstats.find { it.stat_id == 4 }?.base_stat ?: 0,
                statD = data.pokemon_v2_pokemonstats.find { it.stat_id == 5 }?.base_stat ?: 0,
                statS = data.pokemon_v2_pokemonstats.find { it.stat_id == 6 }?.base_stat ?: 0,
                spriteBackDefault = null,
                spriteBackShiny = null,
//                spriteFrontDefault = data.pokemon_v2_pokemonsprites.,
                spriteFrontDefault = null,
                spriteFrontShiny = null,
                spriteBackFemale = null,
                spriteBackShinyFemale = null,
                spriteFrontFemale = null,
                spriteFrontShinyFemale = null
            )
        }
    }
}