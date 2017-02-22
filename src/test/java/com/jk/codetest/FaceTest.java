package com.jk.codetest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.CoreMatchers.is;

import static com.jk.codetest.Face.CHIN;
import static com.jk.codetest.Face.EYES_AND_EARS;
import static com.jk.codetest.Face.HAIR;
import static com.jk.codetest.Face.MOUTH_AND_JAWS;
import static com.jk.codetest.Face.NOSE_AND_CHEEKS;

public class FaceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldFailWhenNoneOfTheFaceDataIsProvided() throws Exception {
        // fail
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Hair should be provided");

        // Given no face data is provided
        // When
        new Face.Builder().build();
        // Then fail
    }

    @Test
    public void shouldFailWhenHairIsMissing() throws Exception {
        // fail
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Hair should be provided");

        // Given hair is not provided
        // When
        new Face.Builder()
                        .withEyesAndEars("o o")
                        .withNoseAndCheeks(" ^ ")
                        .withMouthAndJaws("| - |")
                        .withChin("--")
                        .build();
        // Then fail
    }

    @Test
    public void shouldFailWhenEyesAndEarsAreMissing() throws Exception {
        // fail
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Eyes and ears should be provided");

        // Given eyes and ears are not provided
        // When
        new Face.Builder()
                        .withHair("''''")
                        .withNoseAndCheeks(" ^ ")
                        .withMouthAndJaws("| - |")
                        .withChin("--")
                        .build();
        // Then fail
    }

    @Test
    public void shouldFailWhenNoseAndCheeksAreMissing() throws Exception {
        // fail
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Nose and cheeks should be provided");

        // Given nose and cheeks are not provided
        // When
        new Face.Builder()
                        .withHair("''''")
                        .withEyesAndEars("o o")
                        .withMouthAndJaws("| - |")
                        .withChin("--")
                        .build();
        // Then fail
    }

    @Test
    public void shouldFailWhenMouthAndJawsAreMissing() throws Exception {
        // fail
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Mouth and jaws should be provided");

        // Given mouth and jaws are not provided
        // When
        new Face.Builder()
                        .withHair("''''")
                        .withEyesAndEars("o o")
                        .withNoseAndCheeks(" ^ ")
                        .withChin("--")
                        .build();
        // Then fail
    }

    @Test
    public void shouldFailWhenChinIsMissing() throws Exception {
        // fail
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Chin should be provided");

        // Given chin is not provided
        // When
        new Face.Builder()
                        .withHair("''''")
                        .withEyesAndEars("o o")
                        .withMouthAndJaws("| - |")
                        .withNoseAndCheeks(" ^ ")
                        .build();
        // Then fail
    }

    @Test
    public void shouldFailWhenGivenHairIsInvalid() throws Exception {
        // fail
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid hair pattern provided '''', expected pattern is  +\"\"\"\"\"+ ");

        // Given hair is invalid
        Face face = new Face.Builder()
                        .withHair("''''")
                        .withEyesAndEars("o o")
                        .withMouthAndJaws("| - |")
                        .withNoseAndCheeks(" ^ ")
                        .withChin("|   |")
                        .build();

        // When
        face.renderFace();

        // Then fail
    }

    @Test
    public void shouldFailWhenEyesAndEarsIsInvalid() throws Exception {
        // fail
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid eyes and ears pattern provided o o, expected pattern is [| o o |]");

        // Given hair is invalid
        Face face = new Face.Builder()
                        .withHair(HAIR)
                        .withEyesAndEars("o o")
                        .withMouthAndJaws("| - |")
                        .withNoseAndCheeks(" ^ ")
                        .withChin("|   |")
                        .build();

        // When
        face.renderFace();

        // Then fail
    }

    @Test
    public void shouldFailWhenNoseAndCheeksIsInvalid() throws Exception {
        // fail
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid nose and cheeks pattern provided  ^ , expected pattern is  |  ^  | ");

        // Given hair is invalid
        Face face = new Face.Builder()
                        .withHair(HAIR)
                        .withEyesAndEars(EYES_AND_EARS)
                        .withNoseAndCheeks(" ^ ")
                        .withMouthAndJaws("| - |")
                        .withChin("|   |")
                        .build();

        // When
        face.renderFace();

        // Then fail
    }

    @Test
    public void shouldFailWhenMouthAndJawsIsInvalid() throws Exception {
        // fail
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid mouth and jaws pattern provided | - |, expected pattern is  | '-' | ");

        // Given hair is invalid
        Face face = new Face.Builder()
                        .withHair(HAIR)
                        .withEyesAndEars(EYES_AND_EARS)
                        .withNoseAndCheeks(NOSE_AND_CHEEKS)
                        .withMouthAndJaws("| - |")
                        .withChin("|   |")
                        .build();

        // When
        face.renderFace();

        // Then fail
    }

    @Test
    public void shouldFailWhenChinIsInvalid() throws Exception {
        // fail
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid chin pattern provided |   |, expected pattern is  +-----+ ");

        // Given hair is invalid
        Face face = new Face.Builder()
                        .withHair(HAIR)
                        .withEyesAndEars(EYES_AND_EARS)
                        .withNoseAndCheeks(NOSE_AND_CHEEKS)
                        .withMouthAndJaws(MOUTH_AND_JAWS)
                        .withChin("|   |")
                        .build();

        // When
        face.renderFace();

        // Then fail
    }

    @Test
    public void shouldRenderFaceWhenValidFaceIsProvidec() throws Exception {
        // Given
        Face face = new Face.Builder()
                        .withHair(HAIR)
                        .withEyesAndEars(EYES_AND_EARS)
                        .withNoseAndCheeks(NOSE_AND_CHEEKS)
                        .withMouthAndJaws(MOUTH_AND_JAWS)
                        .withChin(CHIN)
                        .build();

        // When
        String renderedFace = face.renderFace();

        // Then
        assertThat(renderedFace, is("\n +\"\"\"\"\"+ \n[| o o |]\n |  ^  | \n | '-' | \n +-----+ "));
    }
}