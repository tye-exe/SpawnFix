### SpawnFix:
This plugin is for overriding any other plugins that mess with the default spawn / login behaviour of players.  
  
To set the default spawn location you can either edit the config file manually see [default](#default), or go to the desired position in game & run "/sf setSpawn", which will set the default spawn to the location you're standing at, in the world you're standing in. This will also copy the direction that you are looking in.  
  
On the first ever join a player is teleported to the cords set in [default](#default). For every other login the player is teleported to their last login location. See [login](#login) for ways to control this behaviour.  
For respawning a player is teleported to their default respawn location, or if no spawn is set they are teleported to the [default](#default) location. See [onSpawn](#onspawn) for ways to control this behaviour.
  
The overriding works by repeatedly teleporting a player to their login / respawn location. The settings in [teleport](#teleport) can be used to control the teleportation.  
  
If you want me to make a change to this plugin or to port it to another version, either open an [issue on GitHub](https://github.com/Mapty231/SpawnFix/issues) or send me a friend request on [discord](https://discordapp.com/users/710186242196897946).  
  
Supports spigot based servers from 1.17 - 1.20.2 (at time of writing).  

### Command Guide:
- setSpawn - Sets the spawn-override position to your current location.
- tp - Teleports the player to the default spawn location for SpawnFix.
- reload - Forces SpawnFix to rescan the config files for any changes.
- help - Shows list of what each command does whilst in the game.


### Config guide:
##### default:
- worldName (name of world) - This determines which world is the default spawn one for the player from the name of the world folder. For example, to have the over-world as the default spawn set this to "world" (This is the default over-world name for spigot. Check server.properties for your world name.). Or if you wanted it to be the nether use "world_nether".
- x (any decimal number) - The x pos of the default spawn location desired.
- y (any decimal number) - The y pos of the default spawn location desired.
- z (any decimal number) - The z pos of the default spawn location desired.
- yaw (any decimal number) - The yaw of the default spawn location desired.
- pitch (any decimal number) - The pitch of the default spawn location desired.

#### teleport:
- times (any whole number) - The amount of times to teleport the player to their last login / respawn location.
- retryInterval (any whole number) - How many ticks to wait between each teleport of the player.

#### login:
- "every" - The player will get force-teleported to their last login location on every login.
- "first" - The player will get force-teleported to their last login location only on the first join since a reload or restart.
- "never" - The player will never get force-teleported to their last login location.

#### onSpawn:
- "every" - The player will get force-teleported to their spawn location on every respawn.
- "never" - The player will never get force-teleported to their spawn location.