package io.github.kosmx.playermover.common;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MoveMessage {
    @Setter @Getter
    private UUID player;
    @Getter @Setter
    private String server;

    public static final String channelID = "playermover:move";

    public void read(byte @NotNull [] bytes){
        var byteBuffer = ByteBuffer.wrap(bytes);
        player = UUID.fromString(readString(byteBuffer));
        server = readString(byteBuffer);
    }

    public byte @NotNull [] write(){
        byte[] bytes0 = writeString(player.toString());
        byte[] bytes1 = writeString(server);
        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES*2 + bytes0.length + bytes1.length);
        byteBuffer.putInt(bytes0.length);
        byteBuffer.put(bytes0);
        byteBuffer.putInt(bytes1.length);
        byteBuffer.put(bytes1);
        return byteBuffer.array();
    }


    @NotNull
    public static String readString(@NotNull ByteBuffer byteBuffer) throws BufferUnderflowException, BufferOverflowException {
        int len = byteBuffer.getInt();
        byte[] bytes = new byte[len];
        byteBuffer.get(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static void writeString(@NotNull ByteBuffer byteBuffer, @NotNull String string) throws BufferUnderflowException, BufferOverflowException {
        byte[] bytes = writeString(string);
        byteBuffer.putInt(bytes.length);
        byteBuffer.put(bytes);
    }

    public static byte @NotNull [] writeString(@NotNull String string) throws BufferUnderflowException, BufferOverflowException {
        return string.getBytes(StandardCharsets.UTF_8);
    }
}
