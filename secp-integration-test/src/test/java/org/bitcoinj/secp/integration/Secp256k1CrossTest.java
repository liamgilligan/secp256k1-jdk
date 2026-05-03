/*
 * Copyright 2023-2026 secp256k1-jdk Developers.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bitcoinj.secp.integration;

import org.bitcoinj.secp.SecpPrivKey;
import org.bitcoinj.secp.SecpPubKey;
import org.bitcoinj.secp.ffm.Secp256k1Foreign;
import org.bitcoinj.secp.internal.SecpPrivKeyImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;
import static org.bitcoinj.secp.integration.SecpTestSupport.parseHex;

import java.util.List;


/**
 *
 */
public class Secp256k1CrossTest {
    static final Secp256k1Foreign secpFor = new Secp256k1Foreign();
    static final Secp256k1Foreign secpBC = new Secp256k1Foreign();

    static List<byte[]> privateKeys = List.of(
            parseHex("0000000000000000000000000000000000000000000000000000000000000003"),
            parseHex("B7E151628AED2A6ABF7158809CF4F3C762E7160F38B4DA56A784D9045190CFEF"),
            parseHex("C90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B14E5C9")
    );

    @ParameterizedTest
    @FieldSource("privateKeys")
    public void pubKeyGenTest(byte[] privateKey) {
        SecpPrivKey key = new SecpPrivKeyImpl(privateKey);
        SecpPubKey pubKeyForeign = secpFor.ecPubKeyCreate(key);
        SecpPubKey pubKeyBC = secpBC.ecPubKeyCreate(key);

        Assertions.assertArrayEquals(pubKeyForeign.serialize(), pubKeyBC.serialize());
    }
}
