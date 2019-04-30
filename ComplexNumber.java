/**
 * A complex number class
 * @version 0.1
 * @author Constantine Ter-Matevosian
 * Many more methods will be added in v0.2
 */

public class ComplexNumber {

    private double real;
    private double imaginary;
    private double distance;
    private double argument;

    /**
     * A constructor is given three values, the functionality of the first two of which depends on a checker string.
     * If checker is "cartesian", the first two values play roles of real and imaginary parts correspondingly.
     * If checker is "polar" - the distance and the argument correspondingly.
     */

    public ComplexNumber(double first, double second, String checker) throws ComplexNumberException {
        if (!checker.equals("cartesian") && !checker.equals("polar")) {
            throw new ComplexNumberException("Undefined coordinate system");
        } else if (checker.equals("polar") && first < 0) {
            throw new ComplexNumberException("Negative distance in the constructor");
        }
        if (checker.equals("cartesian")) {
            real = first;
            imaginary = second;
            updatePolarVariables();
        } else {
            distance = first;
            argument = second;
            updateCartesianVariables();
        }
    }

    /**
     * Argument of a complex number runs over a semi-closed interval (-pi, pi].
     * Distance is never negative.
     * No restrictions on real and imaginary parts.
     */

    private void updatePolarVariables() {
        distance = Math.sqrt(real * real + imaginary * imaginary);
        argument = real == 0 && imaginary == 0
                ? 0
                : (real > 0
                ? Math.atan(imaginary / real)
                : (real < 0 && imaginary > 0
                ? Math.atan(imaginary / real) + Math.PI
                : (real < 0 && imaginary == 0
                ? Math.PI
                : Math.atan(imaginary / real) - Math.PI)));
    }

    private void updateCartesianVariables() {
        real = distance * Math.cos(argument);
        imaginary = distance * Math.sin(argument);
    }

    public double getRealPart() {
        return real;
    }

    public double getImaginaryPart() {
        return imaginary;
    }

    public double getDistance() {
        return distance;
    }

    public double getArgument() {
        return argument;
    }

    public void setRealPart(double value) {
        real = value;
        updatePolarVariables();
    }

    public void setImaginaryPart(double value) {
        imaginary = value;
        updatePolarVariables();
    }

    public void setDistance(double value) throws ComplexNumberException{
        if (value < 0) {
            throw new ComplexNumberException("Negative distance in the setter");
        }
        distance = value;
        updateCartesianVariables();
    }

    public void setArgument(double value) {
        while (value >= Math.PI) {
            value -= 2 * Math.PI;
        }
        while (value < -Math.PI) {
            value += 2 * Math.PI;
        }
        argument = value;
        updateCartesianVariables();
    }

    public void add(ComplexNumber summand) {
        real += summand.real;
        imaginary += summand.imaginary;
        updatePolarVariables();
    }

    public void subtract(ComplexNumber summand) {
        real -= summand.real;
        imaginary -= summand.imaginary;
        updatePolarVariables();
    }

    public void multiply(ComplexNumber summand) {
        distance *= summand.distance;
        argument += summand.argument;
        updateCartesianVariables();
    }

    public void divide(ComplexNumber summand) {
        divisionByZero(summand);
        distance /= summand.distance;
        argument -= summand.argument;
        updateCartesianVariables();
    }

    private void divisionByZero(ComplexNumber number) throws ArithmeticException {
        if (number.distance == 0.0) {
            throw new ArithmeticException("Division by zero");
        }
    }
}
