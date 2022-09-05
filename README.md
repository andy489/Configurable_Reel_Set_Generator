# ConfigurableReelSetGeneratorForClusterSlotMachine
Tile proportion preserving rolls generator for slot machine with cluster payout strategy

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
