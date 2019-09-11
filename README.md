![alt text](https://proxy.spigotmc.org/1998f98e51685efd529a2e95ace9d3f3de88e8b4?url=https%3A%2F%2Fi.imgur.com%2FoqVaPoF.jpg)


# Villages
Villages is a unique and simple land claiming plugin that allows you to group up with members to build on your survival world.


## Features

1. Efficient GSON/YAML data serialization.
2. Request system (KICK, DISBAND, INVITE).
3. JSON message handling.
4. Add/Remove Members.
5. Effective Chunk claiming.
6. Title display when switching chunks.
7. Information about villages.
8. Break, Place, Interation listeners.
9. An effective village management system.


## Configuration

The configuration files have been formatted into YAML so that it makes it more readable for commercial use.
We highly recommend to not configure any data in the **villages.yml** file unless you know what you are doing.

Configuration files:
- messages.yml
- villages.yml (Not Recommended to configure)


## Commands and Permissions

Commands are very easy to use in regards to permissions.
```
Key: village.{command}

Examples:
- /village create | village.create
- /village disband | village.disband
```

Permissions/Flags:
```
ARMOR_STAND_ACCESS
BLOCK_BREAK
BLOCK_PLACE
BREWING_ACCESS
CHEST_ACCESS
CLAIM_LAND
DISBAND
DRAGON_EGG_TOUCH
EDIT_PERMISSIONS
FURNACE_ACCESS
HOME
INVITE_MEMBER
KICK_MEMBER
LAVA_PLACEMENT
SET_DESCRIPTION
SET_HOME
SHULKER_ACCESS
UNCLAIM_LAND
WATER_PLACEMENT
```


## Upcoming Features

Since villages is still in ALPHA we are still working hard on releasing more features to improve the plugin. These are a list of potential upcoming featues:
- [x] Request System
- [x] General Settings
- [ ] Resources management (Special Feature)
- [x] Flags and Permissions

## Developer API

As of Beta-5.4 this Plugin now has a Developer API that you can utilise in your projects to hook into Villages.
If you are not using Maven, then just skip the first two steps.

### 1. Adding the repository

Since this Plugin is available on GitHub and doesn't have it's own maven-repository, you can just add the jitpack.io repository if you haven't already.
Add this part into your ```<repositories>``` section.

```xml
<repository>
  <id>jitpack.io</id>
  <url>https://jitpack.io</url>
</repository>
```

### 2. Adding the dependency

Now you just have to add the dependency itself to your pom.xml, just add this part into your ```<dependencies>``` section.

```xml
<dependency>
  <groupId>com.github.Stefthedev</groupId>
  <artifactId>Villages</artifactId>
  <version>master-SNAPSHOT</version>
</dependency>
```

### 3. Accessing the API

Villages has a static utility class called "VillagesAPI".
In there you can access the following methods, if they aren't self-explaining, then read up the javadoc-annotation on them in your IDE.

```java
Optional<Village> getVillage(Chunk chunk);
Optional<Village> getVillage(Player player);
Optional<Village> getVillage(UUID playerUUID);
Optional<Village> getVillage(String villageName);
boolean hasPermission(UUID playerUUID, Village village, VillagePermission permission);
```

## Support

If you have any issues or concerns in regards to the plugin feel free to message me on these platforms:
- **Discord:** Stef#0001
- **Spigot:** StefTheDev

No issues or bugs have been found at this stage.


