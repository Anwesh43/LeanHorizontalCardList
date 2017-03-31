# LeanHorizontalCardList

## Android library to create an horizontalcardlist

## Usage

### In ActivityFile, create object of LeanCardContainer

```
    LeanCardContainer leanCardContainer = new LeanCardContainer(this);
```

### Creating a LeanCard

```
    LeanCard leanCard = new LeanCard(bitmap,titleString,subtitleString);
```

### Adding LeanCard in LeanCardContainer

```
    leanCardContainer.addLeanCard(leanCard);
```

### showing lean card container
```
    leanCardContainer.show();
```
