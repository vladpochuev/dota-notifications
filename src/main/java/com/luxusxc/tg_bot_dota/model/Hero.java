package com.luxusxc.tg_bot_dota.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum Hero {
    ANTI_MAGE(1, "Anti-Mage"),
    AXE(2, "Axe"),
    BANE(3, "Bane"),
    BLOODSEEKER(4, "Bloodseeker"),
    CRYSTAL_MAIDEN(5, "Crystal Maiden"),
    DROW_RANGER(6, "Drow Ranger"),
    EARTHSHAKER(7, "Earthshaker"),
    JUGGERNAUT(8, "Juggernaut"),
    MIRANA(9, "Mirana"),
    MORPHLING(10, "Morphling"),
    SHADOW_FIEND(11, "Shadow Fiend"),
    PHANTOM_LANCER(12, "Phantom Lancer"),
    PUCK(13, "Puck"),
    PUDGE(14, "Pudge"),
    RAZOR(15, "Razor"),
    SAND_KING(16, "Sand King"),
    STORM_SPIRIT(17, "Storm Spirit"),
    SVEN(18, "Sven"),
    TINY(19, "Tiny"),
    VENGEFUL_SPIRIT(20, "Vengeful Spirit"),
    WINDRANGER(21, "Windranger"),
    ZEUS(22, "Zeus"),
    KUNKKA(23, "Kunkka"),
    LINA(25, "Lina"),
    LION(26, "Lion"),
    SHADOW_SHAMAN(27, "Shadow Shaman"),
    SLARDAR(28, "Slardar"),
    TIDEHUNTER(29, "Tidehunter"),
    WITCH_DOCTOR(30, "Witch Doctor"),
    LICH(31, "Lich"),
    RIKI(32, "Riki"),
    ENIGMA(33, "Enigma"),
    TINKER(34, "Tinker"),
    SNIPER(35, "Sniper"),
    NECROPHOS(36, "Necrophos"),
    WARLOCK(37, "Warlock"),
    BEASTMASTER(38, "Beastmaster"),
    QUEEN_OF_PAIN(39, "Queen of Pain"),
    VENOMANCER(40, "Venomancer"),
    FACELESS_VOID(41, "Faceless Void"),
    WRAITH_KING(42, "Wraith King"),
    DEATH_PROPHET(43, "Death Prophet"),
    PHANTOM_ASSASSIN(44, "Phantom Assassin"),
    PUGNA(45, "Pugna"),
    TEMPLAR_ASSASSIN(46, "Templar Assassin"),
    VIPER(47, "Viper"),
    LUNA(48, "Luna"),
    DRAGON_KNIGHT(49, "Dragon Knight"),
    DAZZLE(50, "Dazzle"),
    CLOCKWERK(51, "Clockwerk"),
    LESHRAC(52, "Leshrac"),
    NATURES_PROPHET(53, "Nature's Prophet"),
    LIFESTEALER(54, "Lifestealer"),
    DARK_SEER(55, "Dark Seer"),
    CLINKZ(56, "Clinkz"),
    OMNIKNIGHT(57, "Omniknight"),
    ENCHANTRESS(58, "Enchantress"),
    HUSKAR(59, "Huskar"),
    NIGHT_STALKER(60, "Night Stalker"),
    BROODMOTHER(61, "Broodmother"),
    BOUNTY_HUNTER(62, "Bounty Hunter"),
    WEAVER(63, "Weaver"),
    JAKIRO(64, "Jakiro"),
    BATRIDER(65, "Batrider"),
    CHEN(66, "Chen"),
    SPECTRE(67, "Spectre"),
    ANCIENT_APPARITION(68, "Ancient Apparition"),
    DOOM(69, "Doom"),
    URSA(70, "Ursa"),
    SPIRIT_BREAKER(71, "Spirit Breaker"),
    GYROCOPTER(72, "Gyrocopter"),
    ALCHEMIST(73, "Alchemist"),
    INVOKER(74, "Invoker"),
    SILENCER(75, "Silencer"),
    OUTWORLD_DESTROYER(76, "Outworld Destroyer"),
    LYCAN(77, "Lycan"),
    BREWMASTER(78, "Brewmaster"),
    SHADOW_DEMON(79, "Shadow Demon"),
    LONE_DRUID(80, "Lone Druid"),
    CHAOS_KNIGHT(81, "Chaos Knight"),
    MEEPO(82, "Meepo"),
    TREANT_PROTECTOR(83, "Treant Protector"),
    OGRE_MAGI(84, "Ogre Magi"),
    UNDYING(85, "Undying"),
    RUBICK(86, "Rubick"),
    DISRUPTOR(87, "Disruptor"),
    NYX_ASSASSIN(88, "Nyx Assassin"),
    NAGA_SIREN(89, "Naga Siren"),
    KEEPER_OF_THE_LIGHT(90, "Keeper of the Light"),
    IO(91, "Io"),
    VISAGE(92, "Visage"),
    SLARK(93, "Slark"),
    MEDUSA(94, "Medusa"),
    TROLL_WARLORD(95, "Troll Warlord"),
    CENTAUR_WARRUNNER(96, "Centaur Warrunner"),
    MAGNUS(97, "Magnus"),
    TIMBERSAW(98, "Timbersaw"),
    BRISTLEBACK(99, "Bristleback"),
    TUSK(100, "Tusk"),
    SKYWRAHT_MAGE(101, "Skywrath Mage"),
    ABADDON(102, "Abaddon"),
    ELDER_TITAN(103, "Elder Titan"),
    LEGION_COMMANDER(104, "Legion Commander"),
    TECHIES(105, "Techies"),
    EMBER_SPIRIT(106, "Ember Spirit"),
    EARTH_SPIRIT(107, "Earth Spirit"),
    UNDERLORD(108, "Underlord"),
    TERRORBLADE(109, "Terrorblade"),
    PHOENIX(110, "Phoenix"),
    ORACLE(111, "Oracle"),
    WINTER_WYVERN(112, "Winter Wyvern"),
    ARC_WARDEN(113, "Arc Warden"),
    MONKEY_KING(114, "Monkey King"),
    DARK_WILLOW(119, "Dark Willow"),
    PANGOLIER(120, "Pangolier"),
    GRIMSTROKE(121, "Grimstroke"),
    HOODWINK(123, "Hoodwink"),
    VOID_SPIRIT(126, "Void Spirit"),
    SNAPFIRE(128, "Snapfire"),
    MARS(129, "Mars"),
    DAWNBREAKER(135, "Dawnbreaker"),
    MARCI(136, "Marci"),
    PRIMAL_BEAST(137, "Primal Beast"),
    MUERTA(138, "Muerta");

    private final int id;
    private final String name;
    private static final Map<Integer, Hero> ID_TO_HERO;

    static {
        ID_TO_HERO = new HashMap<>();
        for (Hero hero : Hero.values()) {
            ID_TO_HERO.put(hero.id, hero);
        }
    }

    Hero(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Hero getById(int id) {
        return ID_TO_HERO.get(id);
    }
}

