package com.app.view;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

public class EmojiImage {

    private static final Map<String, Image> emojiToImage = new HashMap<>();

    static {
        emojiToImage.put("🔳", newImage("/img/wall.png"));
        emojiToImage.put("💀", newImage("/img/skeleton.png"));
        emojiToImage.put("🐱‍👤", newImage("/img/player.png"));
        emojiToImage.put("🤖", newImage("/img/hunter.png"));
        emojiToImage.put("🔮", newImage("/img/end.png"));
        emojiToImage.put("🌀", newImage("/img/spawn.png"));
        emojiToImage.put("📀", newImage("/img/coin.png"));
        emojiToImage.put("🌑", newImage("/img/box.png"));
        emojiToImage.put("💣", newImage("/img/bomb.png"));
        emojiToImage.put("💫", newImage("/img/hole.png"));
        emojiToImage.put("⏱ ", newImage("/img/hourglass.png"));
        emojiToImage.put("🗡 ", newImage("/img/sword.png"));
        emojiToImage.put("❤ ", newImage("/img/heart.png"));
        emojiToImage.put("🔗", newImage("/img/trap.png"));
        emojiToImage.put("👾", newImage("/img/monster.png"));
        emojiToImage.put("👻", newImage("/img/ghost.png"));
        emojiToImage.put("🔐", newImage("/img/locked_door.png"));
        emojiToImage.put("  ", null);
        emojiToImage.put("🎭", newImage("/img/swap.png"));
        emojiToImage.put("🗝 ", newImage("/img/lockpicking.png"));
        emojiToImage.put("✨", newImage("/img/teleportation.png"));
    }

    private static Image newImage(String url){
        return new Image(GameView.class.getResource(url).toExternalForm());
    }

    public static Image getImage(String emoji){
        Image image = emojiToImage.get(emoji);
        return image;
    }
    
}
