package com.jk.codetest;

import java.util.List;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;

import static com.google.common.base.Preconditions.checkArgument;

public class Face {
    private static final Pattern HAIR_PATTERN = Pattern.compile("^\\s\\+\"{5}\\+\\s$");
    private static final Pattern EYES_AND_EARS_PATTERN = Pattern.compile("^\\[|\\so\\so\\s|\\]$");
    private static final Pattern NOSE_AND_CHEEKS_PATTERN = Pattern.compile("^\\s\\|\\s{2}\\^\\s{2}\\|\\s$");
    private static final Pattern MOUTH_AND_JAWS_PATTERN = Pattern.compile("^\\s\\|\\s'-'\\s\\|\\s$");
    private static final Pattern CHIN_PATTERN = Pattern.compile("^\\s\\+-{5}\\+\\s$");

    static final String HAIR = " +\"\"\"\"\"+ ";
    static final String EYES_AND_EARS = "[| o o |]";
    static final String NOSE_AND_CHEEKS = " |  ^  | ";
    static final String MOUTH_AND_JAWS = " | '-' | ";
    static final String CHIN = " +-----+ ";

    private String hair;
    private String eyesAndEars;
    private String noseAndCheeks;
    private String mouthAndJaws;
    private String chin;

    private Face(Builder builder) {
        this.hair = builder.hair;
        this.eyesAndEars = builder.eyesAndEars;
        this.noseAndCheeks = builder.noseAndCheeks;
        this.mouthAndJaws = builder.mouthAndJaws;
        this.chin = builder.chin;
    }

    public static Face defaultFace() {
        return new Face.Builder()
                        .withHair(HAIR)
                        .withEyesAndEars(EYES_AND_EARS)
                        .withNoseAndCheeks(NOSE_AND_CHEEKS)
                        .withMouthAndJaws(MOUTH_AND_JAWS)
                        .withChin(CHIN)
                        .build();
    }

    public String getHair() {
        return hair;
    }

    public String getEyesAndEars() {
        return eyesAndEars;
    }

    public String getNoseAndCheeks() {
        return noseAndCheeks;
    }

    public String getMouthAndJaws() {
        return mouthAndJaws;
    }

    public String getChin() {
        return chin;
    }

    public String renderFace() {
        validateFace();
        List<String> faceAttributes = Lists.newArrayList(this.hair, this.eyesAndEars, this.noseAndCheeks, this.mouthAndJaws, this.chin);
        return faceAttributes.stream().reduce("", (a, b) -> a + "\n" + b);
    }

    private void validateFace() {
        validateHair();
        validateEyesAndEars();
        validateNoseAndCheeks();
        validateMouthAndJaws();
        validateChin();
    }

    private void validateChin() {
        checkArgument(CHIN_PATTERN.matcher(this.chin).find(),
                        "Invalid chin pattern provided " + this.chin + ", expected pattern is " + CHIN);
    }

    private void validateMouthAndJaws() {
        checkArgument(MOUTH_AND_JAWS_PATTERN.matcher(this.mouthAndJaws).find(),
                        "Invalid mouth and jaws pattern provided " + this.mouthAndJaws + ", expected pattern is " + MOUTH_AND_JAWS);
    }

    private void validateNoseAndCheeks() {
        checkArgument(NOSE_AND_CHEEKS_PATTERN.matcher(this.noseAndCheeks).find(),
                        "Invalid nose and cheeks pattern provided " + this.noseAndCheeks + ", expected pattern is " + NOSE_AND_CHEEKS);
    }

    private void validateEyesAndEars() {
        checkArgument(EYES_AND_EARS_PATTERN.matcher(this.eyesAndEars).find(),
                        "Invalid eyes and ears pattern provided " + this.eyesAndEars + ", expected pattern is " + EYES_AND_EARS);
    }

    private void validateHair() {
        checkArgument(HAIR_PATTERN.matcher(this.hair).find(),
                        "Invalid hair pattern provided " + this.hair + ", expected pattern is " + HAIR);
    }

    public static class Builder {
        private String hair;
        private String eyesAndEars;
        private String noseAndCheeks;
        private String mouthAndJaws;
        private String chin;

        public Builder() {
            // intentionally left blank
        }

        public Builder withHair(String hair) {
            this.hair = hair;
            return this;
        }

        public Builder withEyesAndEars(String eyesAndEars) {
            this.eyesAndEars = eyesAndEars;
            return this;
        }

        public Builder withNoseAndCheeks(String noseAndCheeks) {
            this.noseAndCheeks = noseAndCheeks;
            return this;
        }

        public Builder withMouthAndJaws(String mouthAndJaws) {
            this.mouthAndJaws = mouthAndJaws;
            return this;
        }

        public Builder withChin(String chin) {
            this.chin = chin;
            return this;
        }

        public Face build() {
            checkArgument(isNotBlank(this.hair), "Hair should be provided");
            checkArgument(isNotBlank(this.eyesAndEars), "Eyes and ears should be provided");
            checkArgument(isNotBlank(this.noseAndCheeks), "Nose and cheeks should be provided");
            checkArgument(isNotBlank(this.mouthAndJaws), "Mouth and jaws should be provided");
            checkArgument(isNotBlank(this.chin), "Chin should be provided");
            return new Face(this);
        }

        private boolean isNotBlank(String string) {
            return string != null && !string.isEmpty();
        }
    }

    public static void main(String[] args) {
        Face face = Face.defaultFace();

        System.out.println(face.renderFace());
    }

}
