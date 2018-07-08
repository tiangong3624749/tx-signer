package io.bytom.api;

import io.bytom.exception.BytomException;
import io.bytom.http.Client;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class SignaturesImplTest {

    @Test
    public void testSign() throws BytomException {
        String[] privates = new String[1];
        privates[0] = "d343266054d9c8b175d85c755a87b77d44295c5c7e5afb56c5c2efba19d882ae6ddbc4126fcb632d192d67006185b1ce77f1614db167072e0b3cae1f8824cd1a";

        String raw_tx = "070100010161015fc8215913a270d3d953ef431626b19a89adf38e2486bb235da732f0afed515299ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8099c4d59901000116001456ac170c7965eeac1cc34928c9f464e3f88c17d8630240b1e99a3590d7db80126b273088937a87ba1e8d2f91021a2fd2c36579f7713926e8c7b46c047a43933b008ff16ecc2eb8ee888b4ca1fe3fdf082824e0b3899b02202fb851c6ed665fcd9ebc259da1461a1e284ac3b27f5e86c84164aa518648222602013effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff80bbd0ec980101160014c3d320e1dc4fe787e9f13c1464e3ea5aae96a58f00013cffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8084af5f01160014bb93cdb4eca74b068321eeb84ac5d33686281b6500";
        Client client = Client.generateClient();
        RawTransaction rawTransaction = RawTransaction.decode(client, raw_tx);

        String json = "{\n" +
                "  \"allow_additional_actions\": false,\n" +
                "  \"local\": true,\n" +
                "  \"raw_transaction\": \"07010000020161015fb6a63a3361170afca03c9d5ce1f09fe510187d69545e09f95548b939cd7fffa3ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff80fc93afdf01000116001426bd1b851cf6eb8a701c20c184352ad8720eeee90100015d015bb6a63a3361170afca03c9d5ce1f09fe510187d69545e09f95548b939cd7fffa33152a15da72be51b330e1c0f8e1c0db669269809da4f16443ff266e07cc43680c03e0101160014489a678741ccc844f9e5c502f7fac0a665bedb25010003013effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff80a2cfa5df0101160014948fb4f500e66d20fbacb903fe108ee81f9b6d9500013a3152a15da72be51b330e1c0f8e1c0db669269809da4f16443ff266e07cc43680dd3d01160014cd5a822b34e3084413506076040d508bb12232c70001393152a15da72be51b330e1c0f8e1c0db669269809da4f16443ff266e07cc436806301160014a3f9111f3b0ee96cbd119a3ea5c60058f506fb1900\",\n" +
                "  \"signing_instructions\": [\n" +
                "    {\n" +
                "      \"position\": 0,\n" +
                "      \"witness_components\": [\n" +
                "        {\n" +
                "          \"keys\": [\n" +
                "            {\n" +
                "              \"derivation_path\": [\n" +
                "                \"010100000000000000\",\n" +
                "                \"0500000000000000\"\n" +
                "              ],\n" +
                "              \"xpub\": \"ee9dd8affdef7e0cacd0fbbf310217c7f588156c28e414db74c27afaedd8f876cf54547a672b431ff06ee8a146207df9595638a041b55ada1a764a8b5b30bda0\"\n" +
                "            }\n" +
                "          ],\n" +
                "          \"quorum\": 1,\n" +
                "          \"signatures\": null,\n" +
                "          \"type\": \"raw_tx_signature\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"data\",\n" +
                "          \"value\": \"62a73b6b7ffe52b6ad782b0e0efdc8309bf2f057d88f9a17d125e41bb11dbb88\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"position\": 1,\n" +
                "      \"witness_components\": [\n" +
                "        {\n" +
                "          \"keys\": [\n" +
                "            {\n" +
                "              \"derivation_path\": [\n" +
                "                \"010100000000000000\",\n" +
                "                \"0600000000000000\"\n" +
                "              ],\n" +
                "              \"xpub\": \"ee9dd8affdef7e0cacd0fbbf310217c7f588156c28e414db74c27afaedd8f876cf54547a672b431ff06ee8a146207df9595638a041b55ada1a764a8b5b30bda0\"\n" +
                "            }\n" +
                "          ],\n" +
                "          \"quorum\": 1,\n" +
                "          \"signatures\": null,\n" +
                "          \"type\": \"raw_tx_signature\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"data\",\n" +
                "          \"value\": \"ba5a63e7416caeb945eefc2ce874f40bc4aaf6005a1fc792557e41046f7e502f\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        Template template = Template.fromJson(json);
        Signatures signatures = new SignaturesImpl();
        Template result = signatures.generateSignatures(privates, template, rawTransaction);
        System.out.println(result.toJson());
        //通过构造实际交易来验证
    }

    @Test
    public void testSignLocal() throws BytomException {
        String[] privates = new String[1];
//        privates[0] = "28c1fb11f4cfc59417175fdb5e147a6475af0320664b5cd10daf799e67268a522c42a052a728cdaddcb453785d06e54b0ffc8775b46eb320ad96e046e69ad288";
        String derivedXprv = "e8dc6604ae17fcdbee1738855045f0c27a3dc1e6b94d15447a1a1bac86298a522d888be373656a46fb3f6f20b326404d4bf878cd39a126822524724260954494";
        privates[0] = derivedXprv;

        String raw_tx = "0701dfd5c8d505010161015fe54b8d98246bc9d0cfb38849b48aef4e68d0bc44fde26b95f80ff1bff2c3dfc8ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff80d0dbc3f4020101160014e183d88279d1b8d503a5d01e617d0c9da9ae54c5010002013effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff9894d2b0f40201160014c9befb53dc3d040b7e475c4d6d42d411f261830e00013affffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe80701160014613908c28df499e3aa04e033100efaa24ca8fd0100";
        Client client = Client.generateClient();
        RawTransaction rawTransaction = RawTransaction.decode(client, raw_tx);

        String json = "{\n" +
                "        \"raw_transaction\": \"0701dfd5c8d505010161015fe54b8d98246bc9d0cfb38849b48aef4e68d0bc44fde26b95f80ff1bff2c3dfc8ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff80d0dbc3f4020101160014e183d88279d1b8d503a5d01e617d0c9da9ae54c5010002013effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff9894d2b0f40201160014c9befb53dc3d040b7e475c4d6d42d411f261830e00013affffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe80701160014613908c28df499e3aa04e033100efaa24ca8fd0100\",\n" +
                "        \"signing_instructions\": [\n" +
                "            {\n" +
                "                \"position\": 0,\n" +
                "                \"witness_components\": [\n" +
                "                    {\n" +
                "                        \"type\": \"raw_tx_signature\",\n" +
                "                        \"quorum\": 1,\n" +
                "                        \"keys\": [\n" +
                "                            {\n" +
                "                                \"xpub\": \"ba15a4690a34e0a6f8aeabadcbdee0442d76143de0a868a9e47fa386fd86a1302c42a052a728cdaddcb453785d06e54b0ffc8775b46eb320ad96e046e69ad288\",\n" +
                "                                \"derivation_path\": [\n" +
                "                                    \"010300000000000000\",\n" +
                "                                    \"0100000000000000\"\n" +
                "                                ]\n" +
                "                            }\n" +
                "                        ],\n" +
                "                        \"signatures\": null\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"type\": \"data\",\n" +
                "                        \"value\": \"c2b83ad98d86af9a830da8f38471837e08de9b508eb6dea887855b6c6d6c47b7\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ],\n" +
                "        \"allow_additional_actions\": false\n" +
                "    }";
        Template template = Template.fromJson(json);
        Signatures signatures = new SignaturesImpl();
        Template result = signatures.generateSignatures(privates, template, rawTransaction);
        System.out.println(result.toJson());
        //通过构造实际交易来验证
    }
}