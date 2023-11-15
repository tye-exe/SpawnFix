### SpawnFix:
This plugin is for overriding any other plugins that mess with the default spawn / login behaviour of players.  
  
On the first ever join a player is teleported to the cords set in [default](#default). For every other login the player is teleported to their last login location. See [login](#login-one-of-the-words-bellow) for ways to control this behaviour.  
  
The overriding works by repeatedly teleporting a player to their login / respawn location. The settings in [teleport](#teleport) can be used to control this.  
  
If you want me to make a change to this plugin or to port it to another version, either open an [issue on GitHub](https://github.com/Mapty231/SpawnFix/issues) or send me a friend request on [discord](https://discordapp.com/users/710186242196897946).  
  
Supports spigot based servers from 1.17 - 1.20.2 (at time of writing).  

### Config guide:
##### default:
- x (any decimal number) - The x pos of the first ever log in location desired.
- y (any decimal number) - The y pos of the first ever log in location desired.
- z (any decimal number) - The z pos of the first ever log in location desired.

#### teleport:
- times (any whole number) - The amount of times to teleport the player to their last login / respawn location.
- retryInterval (any whole number) - How many ticks to wait between each teleport of the player.

#### login (One of the words below):
- "every" - The player will get teleported to their last login location on every login.
- "first" - The player will get teleported to their last login location only on the first join since a reload or restart.
- "never" - The player will never get teleported to their last login location.

#### onSpawn (true or false):
If set to true the plugin will force the player to respawn at their current spawn point. If the player has set a spawn with a bed or via other means they will be teleported to that. Otherwise, they will e teleported to world spawn.