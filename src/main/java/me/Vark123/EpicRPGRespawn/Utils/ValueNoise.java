package me.Vark123.EpicRPGRespawn.Utils;

import java.util.Random;

public class ValueNoise {
    private final Random random = new Random();

    // Funkcja generująca losową wartość dla danej pozycji
    private double randomValue(int x, int z) {
        random.setSeed(x * 49632 + z * 325176 + 1);
        return random.nextDouble();
    }

    // Interpolacja
    private double interpolate(double a, double b, double t) {
        return a + t * (b - a);
    }

    // Funkcja generująca szum wartości dla danej pozycji
    public double noise(double x, double z) {
        int x0 = (int) Math.floor(x);
        int x1 = x0 + 1;
        int z0 = (int) Math.floor(z);
        int z1 = z0 + 1;

        // Pobierz losowe wartości dla węzłów
        double v00 = randomValue(x0, z0);
        double v01 = randomValue(x0, z1);
        double v10 = randomValue(x1, z0);
        double v11 = randomValue(x1, z1);

        // Oblicz współczynniki interpolacji
        double tx = x - x0;
        double tz = z - z0;

        // Interpolacja
        double i0 = interpolate(v00, v10, tx);
        double i1 = interpolate(v01, v11, tx);

        return interpolate(i0, i1, tz);
    }
}
