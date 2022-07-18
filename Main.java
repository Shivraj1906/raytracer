import java.util.Timer;

public class Main {

    static HittableList randomScene() {
        HittableList world = new HittableList();

        Lambertian groundMaterial = new Lambertian(new Vector3(0.5, 0.5, 0.5));
        world.add(new Sphere(new Vector3(0, -1000, 0), 1000, groundMaterial));

        for (int a = -11; a < 11; a++) {
            for (int b = -11; b < 11; b++) {
                double chooseMaterial = Constants.randomDouble();

                Vector3 center = new Vector3(a + 0.9 * Constants.randomDouble(), 0.2,
                        b + 0.9 * Constants.randomDouble());

                if ((Vector3.subtraction(center, new Vector3(4, 0.2, 0)).length()) > 0.9) {
                    Material sphereMaterial;

                    if (chooseMaterial < 0.8) {
                        // diffuse
                        Vector3 albedo = Vector3.multiplication(Vector3.random(), Vector3.random());
                        sphereMaterial = new Lambertian(albedo);
                    } else if (chooseMaterial < 0.95) {
                        // metal
                        Vector3 albedo = Vector3.random(0.5, 1);
                        double fuzz = Constants.randomDouble(0, 0.5);
                        sphereMaterial = new Metal(albedo, fuzz);
                    } else {
                        // glass
                        sphereMaterial = new Dielectric(1.5);
                    }
                    world.add(new Sphere(center, 0.2, sphereMaterial));
                }
            }
        }

        Dielectric material1 = new Dielectric(1.5);
        world.add(new Sphere(new Vector3(0, 1, 0), 1.0, material1));

        Lambertian material2 = new Lambertian(new Vector3(0.4, 0.2, 0.1));
        world.add(new Sphere(new Vector3(-4, 1, 0), 1.0, material2));

        Metal material3 = new Metal(new Vector3(0.7, 0.6, 0.5), 0.0);
        world.add(new Sphere(new Vector3(4, 1, 0), 1.0, material3));

        return world;
    }

    static Vector3 rayColor(Ray r, Hittable world, int depth) {
        HitRecord rec = new HitRecord();

        if (depth <= 0)
            return new Vector3(0, 0, 0);

        if (world.hit(r, 0.001, Constants.infinity, rec)) {
            Ray scattered = new Ray();
            Vector3 attenuation = new Vector3();

            if (rec.material.scatter(r, rec, attenuation, scattered)) {
                return Vector3.multiplication(attenuation, rayColor(scattered, world, depth - 1));
            }

            return new Vector3(0, 0, 0);
        }
        Vector3 unitDirection = Vector3.unitVector(r.getDirection());
        double t = 0.5 * (unitDirection.getY() + 1.0);
        return Vector3.addition(Vector3.multiplication(1.0 - t, new Vector3(1.0, 1.0, 1.0)),
                Vector3.multiplication(t, new Vector3(0.5, 0.7, 1.0)));
    }

    public static void main(String[] args) {

        // Image
        double aspectRatio = 3.0 / 2;
        int width = 1200;
        int height = (int) (width / aspectRatio);
        int samplePerPixel = 32;
        int maxDepth = 8;

        // World
        HittableList world = randomScene();

        // Camera
        Vector3 lookFrom = new Vector3(13, 2, 3);
        Vector3 lookAt = new Vector3(0, 0, 0);
        Vector3 vup = new Vector3(0, 1, 0);
        double distanceToFocus = 10.0;
        double aperture = 0.1;
        Camera camera = new Camera(lookFrom, lookAt, vup, 20, aspectRatio, aperture, distanceToFocus);

        // Render

        System.out.print("P3\n" + width + " " + height + "\n255\n");

        Timer timer = new Timer();
        TimerHelper helper = new TimerHelper();

        timer.schedule(helper, 1, 1);

        for (int j = height - 1; j >= 0; j--) {
            for (int i = 0; i < width; i++) {
                Vector3 pixelColor = new Vector3(0, 0, 0);
                for (int s = 0; s < samplePerPixel; s++) {
                    double u = (i + Constants.randomDouble()) / (width - 1);
                    double v = (j + Constants.randomDouble()) / (height - 1);

                    Ray r = camera.getRay(u, v);
                    pixelColor = Vector3.addition(pixelColor, rayColor(r, world, maxDepth));
                }
                Vector3.writeColor(pixelColor, samplePerPixel);

            }
        }
        // System.err.println("Done.");
        System.err.println(TimerHelper.ms + " ms");
        timer.cancel();
    }
}