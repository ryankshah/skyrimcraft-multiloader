1.21-0.4.0
==========

Player Map - Accessible from Menu (M)
- Map of the world (within a radius of the player - TODO: all visited chunks)
- Fast Travel to Structures (Map Features)
- Waypoints - TODO: /waypoint set <name>

New shouts/spells:
- Telekinesis (TO FIX)
- Oakflesh
- Candlelight (TO FIX)

New cosmetic environment blocks: (TODO - add more if needed)
- Stones
- Dirts
- Grass Blocks

New functional blocks:
- Atronach Forge (Summon Atronachs using ingredients) (TODO)
- Tanning Rack (Turns animal hides into leather) (TODO)

Entity functionality:
- Dragon fight mechanics (TODO)

New entities:
- Flame Atronach (TODO)
- Frost Atronach (TODO)
- Storm Atronach (TODO)
- Hagraven (TODO)
- Bandit (Melee) (TODO)
- Bandit (Ranger) (TODO)
- Spriggan (TODO)
- Burnt Spriggan (drops Burnt Spriggan Wood) (TODO)

New biomes:
- Ash wastes (TODO: Add Burnt Spriggan, Ash Guardian, Ash Hopper, Ash Spawn, TODO: Add burnt plants,
              Ash Farm Structure (https://hewhogames.wordpress.com/wp-content/uploads/2013/02/2013-02-15_00008.jpg))
- Volcanic Tundra (TODO)
- Tundra Marsh (TODO)
- Pine Forest
- Fall Forest (TODO)
- Rocky (Jagged) Tundra (TODO)

New structures:
- Abandoned Building (TODO)
- Abandoned House (TODO)
- Farm (TODO)
- Abandoned Farm (TODO)
- Two new types of dungeon! (TODO)

Bug Fixes:
- Fixed bug where powers could be casted whenever and not set any cooldown.
- Fixed bug where the game would crash if the client was not in focus on world creation


1.21-0.3.0
==========

Add new plants:
- Cabbage (grown using cabbage seeds, found in chests)
- Ash Yam (grown using ash yam slips, found in chests)
- Jazbay Grape Bush (generates in world)
- Juniper Berry Bush (generates in world)
- Snowberry Bush (generates in world)
- Lavender (generates in world)

Added new foods and oven recipes:
- Ash Yam
- Jazbay Crostata
- Juniper Berry Crostata
- Snowberry Crostata
- Lavender Dumpling
- Chicken Dumpling
- Boiled Creme Treat
- Braided Bread
- Clam Chowder
- Elsweyr Fondue
- Grilled Leeks
- Honey Nut Treat

Added oven recipes for existing foods in earlier versions

Added skill perks and perk tree within skills screen
- Press enter on the skill to view the perks and use mouse click to unlock them
- Hover over each perk to see what they do and the level requirement for specific perks
- This is still a Work In Progress (WIP), so bare with me while this is finished
  to see your perks in action!

Improved spell casting system:
- Added shout chargeup time, better balancing for continuous spells, magicka consumption and instant cast abilities
- Added new shouts/spells:
  - Fire Breath (Shout)

Added new entities:
- Skeever
- Venomfang Skeever
- Vale Sabre Cat (spawns rarely in overworld but spawns in End Highlands)
- Dragon (Normal) -- No fight mechanics yet, to come in next version

Added new potions:
- Potion of Cure Diseases
- Potion of Cure Poison
- Haggling Potions (Potion, Draught, Philter and Elixir) - Improves Trades
- True Shot Potions (Potion, Draught, Philter and Elixir) - Improves Bow Damage

Fixes:
- Fixed ores and world gen features not generating in Fabric version.
- Fixed loot tables not appearing in game in Fabric version.
- Fixed rendering issues with semi-transparent blocks (i.e., flowers).
- Fixed custom effect translation keys (en_US).
- Fixed item and armor tiers not holding true to their assigned values (i.e., not doing/reducing enough damage)
- Fixed custom damage types (shock, poison, frost, etc.)


1.21-0.2.1
==========

Update to 1.21 - Multiloader support and fabric port.

Fixed some major crashes.

Curios is currently disabled while a move to Accessories is made, this will be available in the next version.

Dependencies:

Geckolib 4.5.8 for MC 1.21 SmartBrainLib 1.14.5 for MC 1.21 CommonNetwork 1.0.16 for MC 1.21