package jp.float1251.sample3d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class Sample3d extends ApplicationAdapter {
    ModelBatch batch;
    private PerspectiveCamera camera;
    private Model model;
    private ModelInstance instance;
    private Environment environment;
    private CameraInputController cameraController;
    private Model sphere;
    private ModelInstance sphereInstance;

    @Override
    public void create() {
        batch = new ModelBatch();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, .4f, .4f, .4f, 1f));
        environment.add(new DirectionalLight().set(.8f, .8f, .8f, -1f, -0.8f, -0.2f));

        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(10, 10, 10);
        camera.lookAt(0, 0, 0);
        camera.near = 1f;
        camera.far = 200f;
        camera.update();

        cameraController = new CameraInputController(camera);
        Gdx.input.setInputProcessor(cameraController);

        ModelBuilder builder = new ModelBuilder();
        model = builder.createBox(5f, 5f, 5f,
                new Material(ColorAttribute.createDiffuse(Color.CYAN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instance = new ModelInstance(model);

        sphere = builder.createSphere(5f, 5f, 5f, 20, 20,
                new Material(ColorAttribute.createDiffuse(Color.MAGENTA)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        sphereInstance = new ModelInstance(sphere);
        sphereInstance.transform.translate(new Vector3(8, 0, 0));

    }

    @Override
    public void render() {
        cameraController.update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        batch.begin(camera);
        batch.render(instance, environment);
        batch.render(sphereInstance, environment);
        batch.end();
    }

}
