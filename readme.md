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

### Demo

    <img src="https://github.com/Anwesh43/LeanHorizontalCardList/blob/master/screenshots/leancarddemo.gif" alt="Demo of LeanCardContainer" width="400" height="700"/>
