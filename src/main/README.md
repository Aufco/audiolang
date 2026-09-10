# Audio Lang

A Minecraft 1.21.4 Forge mod that provides audio for all Minecraft translation keys in multiple languages.

## Features

- **🔊 Complete Audio Coverage**: Over 5,400 audio files covering all Minecraft translation keys
- **🌍 Multi-Language Support**: English (en_us) and Spanish Mexican (es_mx) audio
- **🎮 Interactive Keybind**: Press `R` to hear audio for what you're looking at or holding
- **📊 Real-time HUD Display**: On-screen overlay showing:
  - Current coordinates (X Y Z)
  - English translation name
  - Spanish translation name
- **🎯 Smart Context Detection**: 
  - Looking at blocks/entities → plays block/entity audio and shows names
  - Holding item in main hand and not looking at an entity/block → plays item audio and shows names
  - Not looking at an entity/block and not holding an item in main hand → plays current biome audio and shows names
- **🎵 High Quality Audio**: Generated using Google Cloud Text-to-Speech
  - en-US-Chirp3-HD-Achernar
  - es-US-Chirp3-HD-Enceladus
- **⚡ Optimized Format**: All audio converted to Minecraft-compatible Vorbis OGG format
- **🎨 Clean Interface**: Transparent HUD overlay that doesn't interfere with gameplay

## How It Works

The mod uses Minecraft's translation key system to provide both audio feedback and visual display:

### Audio System
1. **Press R key** while in-game
2. The mod detects what you're interacting with:
   - **Block focus**: Plays audio for the block's translation key (e.g., `block.minecraft.stone`)
   - **Entity focus**: Plays audio for the entity's translation key (e.g., `entity.minecraft.cow`)
   - **Item in hand**: Plays audio for the item's translation key (e.g., `item.minecraft.diamond_sword`)
   - **Biome**: Plays audio for your current biome (e.g., `biome.minecraft.plains`)

### HUD Display System
The mod continuously displays in the top-left corner:
- **Coordinates**: Your current X, Y, Z position
- **English Name**: English translation of the current context
- **Spanish Name**: Spanish translation of the current context

Example display:
```
389 86 310
en: Stone
es: Piedra
```

## Installation

1. Download the latest release from [Releases](https://github.com/Aufco/languagelearningmod)
2. Download Recommended [Minecraft Forge - MC 1.21.4](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.21.4.html)
3. Place the mod JAR file in your `mods` folder
4. Launch Minecraft

## Commands

You can also play sounds directly:

```
Format:
/playsound audiolang:<translation_key>_<language>_sound voice @p

Example:
/playsound audiolang:biome.minecraft.plains_en_us_sound voice @p
/playsound audiolang:block.minecraft.stone_es_mx_sound voice @p
/playsound audiolang:item.minecraft.diamond_en_us_sound voice @p
/playsound audiolang:entity.minecraft.pig_es_mx_sound voice @p

Test Sounds:
/playsound audiolang:test_en_us_sound voice @p
/playsound audiolang:test_es_mx_sound voice @p

```

## Keybinds

- **R** - Play Translation Sound (configurable in Controls menu)

## Supported Languages

- **English (US)** - `en_us`
- **Spanish (Mexico)** - `es_mx`

The mod automatically detects your Minecraft language setting and plays the appropriate audio.

## Volume Control

The mod uses the **Voice/Speech** audio category. To control the volume:

1. Go to **Options** → **Sounds**
2. Adjust the **Voice/Speech** slider to control AudioLang mod volume
3. This allows you to balance translation audio independently from other game sounds

## Technical Details

- **Sound Format**: OGG Vorbis, 44.1kHz, Mono
- **Audio Engine**: Minecraft's built-in sound system
- **File Count**: 5,496 audio files (2,748 per language)
- **Total Coverage**: All blocks, items, entities, and biomes

## [CUSTOMIZE MOD](README_MODDER.md)


## Audio Generation

The audio files were generated using:
- **Google Cloud Text-to-Speech API**
- **Voice Models**: Standard voices for natural pronunciation
- **Languages**: English (US) and Spanish (Mexico)
- **Post-processing**: Converted from Opus to Vorbis for Minecraft compatibility

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Built on the Minecraft Forge framework
- Audio generated using Google Cloud Text-to-Speech
- Inspired by the need for accessibility and language learning tools in Minecraft

## Support

- Report bugs: [GitHub Issues](https://github.com/benau/audiolang/issues)
- Discussions: [GitHub Discussions](https://github.com/benau/audiolang/discussions)
