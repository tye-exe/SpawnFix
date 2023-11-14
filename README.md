### SpawnFix:
This project was created to solve an issue one of my friends was having with their mc server.  
What this plugin does is it teleports a player to the cords given in the config.yml on the first ever join.    
Then on a players first join after a reload or restart it teleports them to their last log-off location.

#### Config guide:
##### default:
- x = The x pos of the first ever log in location desired.
- y = The y pos of the first ever log in location desired.
- z = The z pos of the first ever log in location desired.

#### teleport:
- times = The amount of times to teleport the player to their last log in location.
- retryInterval = How many ticks to wait between each teleport of the player.