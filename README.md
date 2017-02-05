Smiths Core
=========

Welcome to the GitHub project page of SmithsCore, SmithsModding library of Choice!
 
## What is SmithsCore:
SmithsCore is a mod you call a Library. It does not add anything you can see to the game directly, but it takes a lot of work away from us Modders. It was created when the members of the SmithsModding-Modding Team noticed that a lot of their mods had features under the hood of their respective mods that were very similar, like a modular GUI-System, saving ItemStacks in TileEntities to disk, synchronizing, and messaging between parts of their mods, and the mods themselves.
 
## What it contains at this point:
### A GUI-System that is based on components instead of Images.
It is capable of using scissoring to only render parts of components if they are obscured by their parents, or if an animation requires it.
It contains a complete animation system that makes sure that each component can manipulate itself.
It is capable of constructing both server and client side Tabbed GUIs, meaning that the server knows which tab of the GUI a player has open.
It keeps track of the Players watching a GUI.
It has the capability to show 'Ledgers', expanding tabs on the side of a GUI, that can contain additional information.
It has a JEI compatibility system build in that moves Items that would be in the way aside.
### A common way of writing to disk and synchronising TileEntities.
### An event driven messaging system that is not only capable of sending messages from one part of a mod to another, or between two mods but it also allows communication between the Client and the Server:
A Common event bus for messages that need to be shared no matter of the side they are relevant for.
A Client event bus for messages that are only relevant for the client side.
A Network event bus on which messages from the 'other side' arrive on.
### A Structure system that allows storing data of Blocks and TileEntities that are part of a structure on a Dimension based level outside of its components, comparable to the Vanilla structure mechanics used for Monuments, Villages, Mineshafts etc. automatically synced between client and server.
### A player manager that keeps track of all players that ever logged in to the Server.
It synchronizes the PlayerID and Name to the client to allow GUIs to display a UserName of a different Player if need be.
### It has a custom model loader that allows multiple Items to use the same model file.
### It has custom texture creation which not only generates a holographic texture for all registered textures, but it can also generate other custom textures if need be.
### It has a system of displaying debug Information on the F3 screen when need be, or always if the game is started in Debug Mode.
### It has a block model update notification system, allowing the server to notify the client that a vanilla model of a block needs to be updated, even when the block state did not change.
### A path finding system that can be used for entities, structures and many other things.
### A whole bunch of utility classes that sometimes just provide convenience methods or wrap other existing classes to provide them with more functionality, or works as a bridge between existing Java classes and Minecraft’s custom implementations.
## What is still coming to SmithsCore:
Right now most of the required features are implemented, yet there are some systems that are missing and which we hope to implement in the future:
A customisable multi block system that extends the current structure system.
A friends system allowing a player to restrict the interaction with all supporting blocks and tile entities.
## What to do if I want to use SmithsCore for my own mod as a modder:
We have a MavenRepo which you can use to download it as a dependency using you build.gradle file:
SmithsCore - MavenRepo
 
## What to do if I find a bug that I think is caused by SmithsCore:
Attempt to reproduce it. Are you not able to reproduce it? Sorry we cannot help you!
If you are able reproduce the Bug, start the client in Debug mode, reproduce the Bug and copy the Log.
Create a GitHub Issue under Issues!
Under NO circumstance post a log or stack trace in CurseForge, we will not process any bugs reported here! But if you have any questions, you can ALWAYS ask here or in IRC esper.net in #SmithsModding.
 
## So how can I activate this aforementioned Debug mode?
Simple: Start your Minecraft instance with an additional Java parameter: -DSmithsModding.Dev=true.
 
## Can I use this Mod in a ModPack:
Yes you can, as long as you give credit back to this page.
 
## What is the license for this Mod:
GLGPL V3
 

## Last Build Status:
  * Development (Minecraft 1.11):      [![Build Status](https://travis-ci.org/SmithsGaming/SmithsCore.svg?branch=Development-1.11)](https://travis-ci.org/SmithsGaming/SmithsCore)

## Nightly Builds:
http://mavenrepo.smithscore.orionminecraft.com/com/SmithsModding/SmithsCore/SmithsCore/

## Requirements:
   *  Minecraft 1.1.11
   *  Minecraft Forge for 1.1.11 (Officialy supported Version: 1.11-13.19.0.2177)

Installation
============
## Users:
  1. Download all the Dependencies and install those.
  2. Drop SmithsCore in the mods folder.
  
## Modders (JetBrains IDEA):
  1. Fork this Repository
  2. Download the fork
  3. Run on a commandline: 
      1. gradlew setupDecompWorkspace
      2. gradlew idea
  4. Inside IDEA:
      1. set ~/resources as 'Resources Root'
