package de.kuschku.libquassel.primitives.serializers;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.common.base.Charsets;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;

public class ByteArraySerializer implements PrimitiveSerializer<String> {
    private static final ByteArraySerializer serializerFalse = new ByteArraySerializer(false);
    private static final ByteArraySerializer serializerTrue = new ByteArraySerializer(true);

    public static ByteArraySerializer get() {
        return get(false);
    }
    public static ByteArraySerializer get(boolean trimLastByte) {
        if (trimLastByte) return serializerTrue;
        else return serializerFalse;
    }

    public final boolean trimLastByte;

    private ByteArraySerializer(boolean trimLastByte) {
        this.trimLastByte = trimLastByte;
    }

    @Override
    public void serialize(final ByteChannel channel, final String data) throws IOException {
        if (data == null) {
            IntSerializer.get().serialize(channel, 0xffffffff);
        } else {
            final ByteBuffer contentBuffer = Charsets.ISO_8859_1.encode(data);
            IntSerializer.get().serialize(channel, contentBuffer.limit() + (trimLastByte ? 1 : 0));
            channel.write(contentBuffer);
            if (trimLastByte) channel.write(ByteBuffer.allocate(1));
        }
    }

    @Nullable
    @Override
    public String deserialize(final ByteBuffer buffer) throws IOException {
        final int len = IntSerializer.get().deserialize(buffer);
        if (len == 0xffffffff)
            return null;
        else if (len == 0)
            return "";
        else {
            final ByteBuffer contentBuffer = ByteBuffer.allocate(len);
            contentBuffer.put(buffer.array(), buffer.position(), len);
            contentBuffer.position(0);
            buffer.position(buffer.position() + len);

            // We have to do this for the usecase of usertype names, as those are serialized with \0 at the end
            if (trimLastByte) {
                // Get rid of the null byte at the end
                contentBuffer.limit(len - 1);
            }
            return Charsets.UTF_8.decode(contentBuffer).toString();
        }
    }
}