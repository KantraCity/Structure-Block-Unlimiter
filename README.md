# Architect's Vision (Structure Block Unlimiter)

[![Версия Minecraft](https://img.shields.io/badge/Minecraft-1.21.1-green.svg?style=for-the-badge)](https://www.minecraft.net)
[![Modloader](https://img.shields.io/badge/Modloader-NeoForge-blue.svg?style=for-the-badge)](https://neoforged.net/)
[![License](https://img.shields.io/badge/License-MIT-lightgrey.svg?style=for-the-badge)](https://opensource.org/licenses/MIT)

A simple, quality-of-life mod that removes the vanilla restrictions on Minecraft's Structure Block, unlocking its true potential for builders, server admins, and creative minds. Save, load, and manage massive structures with ease and precision!

---

## Features

Have you ever tried to save your megastructure, sprawling city, or complex redstone contraption, only to be stopped by the frustrating **48x48x48 block limit**? Architect's Vision fixes that and adds more.

-   ✅ **Fully Configurable Size Limit:** The maximum size of a structure you can save or load is increased from a tiny `48x48x48` to a massive **`512x512x512`** blocks by default. You can change this limit in the config file to whatever your machine can handle!

-   ✅ **Extended Render Distance:** The structure's bounding box is now visible from much further away, increased from `96` to **`256`** blocks by default (also configurable). No more running back and forth to see if your alignment is correct.

-   ✅ **"Re-open Last" Keybind:** A new configurable keybind (default: `G`) allows you to instantly re-open the GUI of the last Structure Block you interacted with, no matter where you are in the world. A huge time-saver for large projects!

-   ✅ **Client + Server:** This mod is required on both the client and the server to enable all features.

-   ✅ **Lightweight & Vanilla-Friendly:** No new blocks, no new items. Just three essential tweaks to a vanilla feature that make it infinitely more powerful.

---
## How It Looks

#### Increased Bounding Box Render Distance
*See your entire project outline from afar.*

![Screenshot of increased render distance](https://imgur.com/1opRaX6.png)

<br>

#### New "Re-open Last" Keybind
*Instantly access the Structure Block GUI from anywhere.*

![Screenshot of keybind usage 1](https://imgur.com/IOSaLsY.png)
![Screenshot of keybind usage 2](https://imgur.com/W2O0ATj.png)

---

## Configuration

This mod is fully configurable. After running the game once, a config file will be generated in `config/structure_block_unlimiter-common.toml`.

```toml

#The maximum size (per axis) that a Structure Block can save or load.
#Vanilla default is 48.
# Default: 512
# Range: > 1
max_structure_size = 512

#The distance in blocks from which the structure's bounding box is visible.
#Vanilla default is 96.
# Default: 256
# Range: > 16
render_distance = 256
```

The keybind can be changed in the standard Minecraft controls menu, under the category **"Structure Block Unlimiter"**.

---

## Installation

This mod is required on both the server and all connecting clients.

1.  Make sure you have [NeoForge](https://neoforged.net/) installed.
2.  Download the latest `.jar` file from the [Releases](https://github.com/KantraCity/Structure-Block-Unlimiter/releases) page.
3.  Place the `.jar` file into your `mods` folder (and the server's `mods` folder if applicable).
4.  Restart the game/server.

## How It Works (For the curious)

This mod uses Mixins to perform its changes. It modifies the `StructureBlockEntity` to allow larger sizes to be saved to disk, patches the `ServerboundSetStructureBlockPacket` to allow sending larger coordinates over the network, and modifies the `StructureBlockRenderer` to increase the render distance. The keybind feature is implemented using NeoForge's event system and custom network packets.

## For Developers & Modpack Creators

You are welcome to include this mod in any modpack! The mod is licensed under the MIT License, so feel free to use it in your projects.

---

*This mod is a utility created to solve a specific vanilla limitation and is designed to be compatible with other mods.*