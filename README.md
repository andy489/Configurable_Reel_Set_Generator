# Configurable Reel Set Generator
Tile proportion preserving rolls generator for slot machine. Very useful for creating no win reels for slot machine with cluster payout strategy, low/hight payout reel sets for slot machine with ways and megaways payout strategy and many more.
Full control of reel shuffling, using stack size and minimal distance for two tiles of same type in different stacks.

## Structure
```
src
└─ main
    └── java
          ├── convert 
          |    ├── CayConverter.java
          |    ├── CountConverter.java
          │    └── EvoConverter.java
          ├── dto 
          |    ├── TemplateJsonReelDefinitions.java
          │    └── TemplateJsonReelSetsEvo.java
          ├── exception 
          │    ├── InvalidJsonFormatException.java
          │    ├── NoSuchCompanyFormatException.java
          │    └── NotSupportedOutputMediaType.java
          ├── io
          │    ├── ReaderManager.java
          │    └── WriterManager.java
          ├── reel 
          │    ├── ReelSet.java
          │    ├── ReelSetEvo.java
          │    ├── ReelSetsCollectionData.java
          │    └── Restriction.java
          ├── rng 
          │    ├── IRNG.java
          │    └── RNG.java
          ├── shuffler 
          |    ├── FlatGenerator.java
          |    ├── RestrictionsApplier.java
          |    └── ShuffleGenerator.java
          ├── wrapper 
          |    └── ConvertWrapper.java
          └── ShuffleWithRestrictions.java
```

## reelDefinitions.json

```json
{
  "mapName": "MOH",
  "gameId": 234,

  "strategy": "shuffle",
  "output": "file",
  "resultFilePath": "./result.txt",

  "convert": {
    "confirm": false,
    "toCom" : "cay",
    "src": "./result.txt",
    "dest": "./result.txt"
  },

  "reelSets": [
    {
      "tilesCounts": [
        [0, 0, 3, 0, 4, 4, 0, 5, 4, 0, 3, 2],
        [0, 0, 3, 3, 0, 4, 7, 0, 4, 3, 0, 2],
        [0, 0, 0, 3, 4, 0, 7, 5, 0, 3, 3, 0],
        [0, 0, 2, 3, 4, 4, 2, 5, 4, 3, 3, 2],
        [0, 0, 2, 3, 4, 4, 1, 5, 4, 3, 3, 2]
      ],
      "restrictions": [
        {"minStack": 1, "maxStack": 3, "distance": 2},
        {"minStack": 2, "maxStack": 4, "distance": 1}
      ]
    },
    {
      "tilesCounts": [
        [0, 0, 5, 2, 4, 6, 0, 5, 4, 4, 3, 2],
        [0, 0, 3, 3, 0, 4, 7, 3, 4, 3, 0, 2],
        [0, 0, 1, 3, 4, 0, 6, 5, 3, 3, 3, 0],
        [0, 0, 2, 3, 4, 4, 2, 5, 4, 3, 3, 2],
        [0, 0, 2, 3, 4, 4, 1, 5, 4, 3, 3, 2]
      ],
      "restrictions": [
        {"minStack": 1, "maxStack": 4, "distance": 1}
      ]
    }
  ]
}

```
As we can observe, we can use alias for property names: restrictions -> res, tileCounts -> cnts and etc. (We can find all alias in reel/Restriction.java).

Let n be the number of reels and k be the number of restrictions.
If n > k, then the i-th restriction applies for all reels, which row number j (0-based enumerating) divided modulo k gives i.
Else the j-th restriction applies for the j-th reel and the last k - n restrictions are ignored.
