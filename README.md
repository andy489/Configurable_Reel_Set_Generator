# ConfigurableReelSetGeneratorForClusterSlotMachine
Tile proportion preserving rolls generator for slot machine. Very useful for creating no win reels for slot machine with cluster payout strategy, low/hight payout reel sets for slot machine with ways and megaways payout strategy and more.
Full control of reel shuffling, using stack size and minimal distance for two tiles of same type in different stacks.

## Structure
```
src
└─ main
    └── java
          ├── dto 
          │    └── TemplateJson.java
          ├── exception 
          │    └── InvalidJsonFormatException.java
          ├── io 
          │    ├── ReaderManager.java
          │    └── WriterManager.java
          ├── reel 
          │    ├── ReelSet.java
          │    ├── ReelSetsCollection.java
          │    └── Restriction.java
          ├── rng 
          │    ├── IRNG.java
          │    └── RNG.java
          ├── shuffler 
          |    ├── FlatGenerator.java
          |    ├── RestrictionsApplier.java
          |    └── ShuffleGenerator.java
          └── ShuffleWithRestrictions.java
```

## reelDefinitions.json

```json
{
  "flat" : false,
  "reelSets": [
    {
      "tilesCounts": [
        [0, 12, 12, 0, 10, 10, 0, 6, 6],
        [12, 0, 12, 10, 0, 10, 6, 0, 6],
        [12, 12, 0, 10, 10, 0, 6, 6, 0],
        [0, 12, 12, 0, 10, 10, 0, 6, 6],
        [12, 0, 12, 10, 0, 10, 6, 0, 6],
        [12, 12, 0, 10, 10, 0, 6, 6, 0],
        [0, 12, 12, 0, 10, 10, 0, 6, 6],
        [12, 0, 12, 10, 0, 10, 6, 0, 6],
        [12, 12, 0, 10, 10, 0, 6, 6, 0]
      ],
      "restrictions": [
        {"minStack": 3, "maxStack": 3, "distance": 3},
        {"minStack": 1, "maxStack": 1, "distance": 2}
      ]
    },
    {
      "tilesCounts": [
        [0, 12, 12, 0, 10, 10, 0, 6, 6],
        [12, 0, 12, 10, 0, 10, 6, 0, 6],
        [12, 12, 0, 10, 10, 0, 6, 6, 0],
        [0, 12, 12, 0, 10, 10, 0, 6, 6],
        [12, 0, 12, 10, 0, 10, 6, 0, 6],
        [12, 12, 0, 10, 10, 0, 6, 6, 0]
      ],
      "restrictions": [
        {"minStack": 4, "maxStack": 4, "distance": 4},
        {"minStack": 2, "maxStack": 2, "distance": 4}
      ]
    }
  ]
}
```
Let n be the number of reels and k be the number of restrictions.
If n > k, then the i-th restriction applies for all reels, which row number j (0-based enumerating) divided modulo k gives i.
Else the j-th restriction applies for the j-th reel and the last k - n restrictions are ignored.
