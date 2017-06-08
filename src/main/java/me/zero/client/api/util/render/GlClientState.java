/*
 * Copyright 2017 ZeroMemes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.zero.client.api.util.render;

import static org.lwjgl.opengl.GL11.*;

public enum GlClientState {

    VERTEX(GL_VERTEX_ARRAY),
    COLOR(GL_COLOR_ARRAY),
    TEXTURE(GL_TEXTURE_COORD_ARRAY);

    public final int cap;

    GlClientState(int cap) {
        this.cap = cap;
    }
}
