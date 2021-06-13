/*
 * Copyright 2012, Google Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *     * Neither the name of Google Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.jfclone.dexlib2.immutable.value;

import org.jfclone.dexlib2.base.value.BaseFieldEncodedValue;
import org.jfclone.dexlib2.iface.value.FieldEncodedValue;
import org.jfclone.dexlib2.immutable.reference.ImmutableFieldReference;

import javax.annotation.Nonnull;

public class ImmutableFieldEncodedValue extends BaseFieldEncodedValue implements ImmutableEncodedValue {
    @Nonnull
    protected final ImmutableFieldReference value;

    public ImmutableFieldEncodedValue(@Nonnull ImmutableFieldReference value) {
        this.value = value;
    }

    public static ImmutableFieldEncodedValue of(@Nonnull FieldEncodedValue fieldEncodedValue) {
        if (fieldEncodedValue instanceof ImmutableFieldEncodedValue) {
            return (ImmutableFieldEncodedValue) fieldEncodedValue;
        }
        return new ImmutableFieldEncodedValue(ImmutableFieldReference.of(fieldEncodedValue.getValue()));
    }

    @Nonnull
    @Override
    public ImmutableFieldReference getValue() {
        return value;
    }
}