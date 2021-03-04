package com.project.git.com.gitproject.svg;

import android.support.annotation.NonNull;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;
import com.caverock.androidsvg.SVG;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Description:
 * @Author: jx_wy
 * @Date: 3/4/21 10:53 AM
 */
public class SvgDecoder implements ResourceDecoder<InputStream, SVG> {

    @Override
    public boolean handles(@android.support.annotation.NonNull InputStream source, @NonNull Options options) {
        // TODO: Can we tell?
        return true;
    }

    public Resource<SVG> decode(
            @NonNull InputStream source, int width, int height, @NonNull Options options)
            throws IOException {
        try {
            SVG svg = SVG.getFromInputStream(source);
            svg.setDocumentWidth(width);
            svg.setDocumentHeight(height);
            return new SimpleResource<>(svg);
        } catch (Exception ex) {
            throw new IOException("Cannot load SVG from stream", ex);
        }
    }
}
