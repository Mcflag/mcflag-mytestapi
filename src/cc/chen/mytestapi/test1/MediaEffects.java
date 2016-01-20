package cc.chen.mytestapi.test1;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.effect.Effect;
import android.media.effect.EffectContext;
import android.media.effect.EffectFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewAnimator;

import cc.chen.mytestapi.BaseActivity;
import cc.chen.mytestapi.R;
import cc.chen.mytestapi.gl.GLToolbox;
import cc.chen.mytestapi.gl.TextureRenderer;

public class MediaEffects extends BaseActivity {
	// Whether the Log Fragment is currently shown
	private boolean mLogShown;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.media_effects_main);

		if (savedInstanceState == null) {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			MediaEffectsFragment fragment = new MediaEffectsFragment();
			transaction.replace(R.id.sample_content_fragment, fragment);
			transaction.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.media_effects_menu_main, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
		logToggle.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
		logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_toggle_log:
			mLogShown = !mLogShown;
			ViewAnimator output = (ViewAnimator) findViewById(R.id.sample_output);
			if (mLogShown) {
				output.setDisplayedChild(1);
			} else {
				output.setDisplayedChild(0);
			}
			supportInvalidateOptionsMenu();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	class MediaEffectsFragment extends Fragment implements GLSurfaceView.Renderer {

		private static final String STATE_CURRENT_EFFECT = "current_effect";

		private GLSurfaceView mEffectView;
		private int[] mTextures = new int[2];
		private EffectContext mEffectContext;
		private Effect mEffect;
		private TextureRenderer mTexRenderer = new TextureRenderer();
		private int mImageWidth;
		private int mImageHeight;
		private boolean mInitialized = false;
		private int mCurrentEffect;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setHasOptionsMenu(true);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
				@Nullable Bundle savedInstanceState) {
			return inflater.inflate(R.layout.media_effects_fragment, container, false);
		}

		@Override
		public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
			mEffectView = (GLSurfaceView) view.findViewById(R.id.effectsview);
			mEffectView.setEGLContextClientVersion(2);
			mEffectView.setRenderer(this);
			mEffectView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
			if (null != savedInstanceState && savedInstanceState.containsKey(STATE_CURRENT_EFFECT)) {
				setCurrentEffect(savedInstanceState.getInt(STATE_CURRENT_EFFECT));
			} else {
				setCurrentEffect(R.id.none);
			}
		}

		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			inflater.inflate(R.menu.media_effects_func_list, menu);
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			setCurrentEffect(item.getItemId());
			mEffectView.requestRender();
			return true;
		}

		@Override
		public void onSaveInstanceState(Bundle outState) {
			outState.putInt(STATE_CURRENT_EFFECT, mCurrentEffect);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
			// Nothing to do here
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			if (mTexRenderer != null) {
				mTexRenderer.updateViewSize(width, height);
			}
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			if (!mInitialized) {
				// Only need to do this once
				mEffectContext = EffectContext.createWithCurrentGlContext();
				mTexRenderer.init();
				loadTextures();
				mInitialized = true;
			}
			if (mCurrentEffect != R.id.none) {
				// if an effect is chosen initialize it and apply it to the
				// texture
				initEffect();
				applyEffect();
			}
			renderResult();
		}

		private void setCurrentEffect(int effect) {
			mCurrentEffect = effect;
		}

		private void loadTextures() {
			// Generate textures
			GLES20.glGenTextures(2, mTextures, 0);

			// Load input bitmap
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.puppy);
			mImageWidth = bitmap.getWidth();
			mImageHeight = bitmap.getHeight();
			mTexRenderer.updateTextureSize(mImageWidth, mImageHeight);

			// Upload to texture
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextures[0]);
			GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

			// Set texture parameters
			GLToolbox.initTexParams();
		}

		private void initEffect() {
			EffectFactory effectFactory = mEffectContext.getFactory();
			if (mEffect != null) {
				mEffect.release();
			}
			// Initialize the correct effect based on the selected menu/action
			// item
			switch (mCurrentEffect) {

			case R.id.none:
				break;

			case R.id.autofix:
				mEffect = effectFactory.createEffect(EffectFactory.EFFECT_AUTOFIX);
				mEffect.setParameter("scale", 0.5f);
				break;

			case R.id.bw:
				mEffect = effectFactory.createEffect(EffectFactory.EFFECT_BLACKWHITE);
				mEffect.setParameter("black", .1f);
				mEffect.setParameter("white", .7f);
				break;

			case R.id.brightness:
				mEffect = effectFactory.createEffect(EffectFactory.EFFECT_BRIGHTNESS);
				mEffect.setParameter("brightness", 2.0f);
				break;

			case R.id.contrast:
				mEffect = effectFactory.createEffect(EffectFactory.EFFECT_CONTRAST);
				mEffect.setParameter("contrast", 1.4f);
				break;

			case R.id.crossprocess:
				mEffect = effectFactory.createEffect(EffectFactory.EFFECT_CROSSPROCESS);
				break;

			case R.id.documentary:
				mEffect = effectFactory.createEffect(EffectFactory.EFFECT_DOCUMENTARY);
				break;

			case R.id.duotone:
				mEffect = effectFactory.createEffect(EffectFactory.EFFECT_DUOTONE);
				mEffect.setParameter("first_color", Color.YELLOW);
				mEffect.setParameter("second_color", Color.DKGRAY);
				break;

			case R.id.filllight:
				mEffect = effectFactory.createEffect(EffectFactory.EFFECT_FILLLIGHT);
				mEffect.setParameter("strength", .8f);
				break;

			case R.id.fisheye:
				mEffect = effectFactory.createEffect(EffectFactory.EFFECT_FISHEYE);
				mEffect.setParameter("scale", .5f);
				break;

			case R.id.flipvert:
				mEffect = effectFactory.createEffect(EffectFactory.EFFECT_FLIP);
				mEffect.setParameter("vertical", true);
				break;

			case R.id.fliphor:
				mEffect = effectFactory.createEffect(EffectFactory.EFFECT_FLIP);
				mEffect.setParameter("horizontal", true);
				break;

			case R.id.grain:
				mEffect = effectFactory.createEffect(EffectFactory.EFFECT_GRAIN);
				mEffect.setParameter("strength", 1.0f);
				break;

			case R.id.grayscale:
				mEffect = effectFactory.createEffect(EffectFactory.EFFECT_GRAYSCALE);
				break;

			case R.id.lomoish:
				mEffect = effectFactory.createEffect(EffectFactory.EFFECT_LOMOISH);
				break;

			case R.id.negative:
				mEffect = effectFactory.createEffect(EffectFactory.EFFECT_NEGATIVE);
				break;

			case R.id.posterize:
				mEffect = effectFactory.createEffect(EffectFactory.EFFECT_POSTERIZE);
				break;

			case R.id.rotate:
				mEffect = effectFactory.createEffect(EffectFactory.EFFECT_ROTATE);
				mEffect.setParameter("angle", 180);
				break;

			case R.id.saturate:
				mEffect = effectFactory.createEffect(EffectFactory.EFFECT_SATURATE);
				mEffect.setParameter("scale", .5f);
				break;

			case R.id.sepia:
				mEffect = effectFactory.createEffect(EffectFactory.EFFECT_SEPIA);
				break;

			case R.id.sharpen:
				mEffect = effectFactory.createEffect(EffectFactory.EFFECT_SHARPEN);
				break;

			case R.id.temperature:
				mEffect = effectFactory.createEffect(EffectFactory.EFFECT_TEMPERATURE);
				mEffect.setParameter("scale", .9f);
				break;

			case R.id.tint:
				mEffect = effectFactory.createEffect(EffectFactory.EFFECT_TINT);
				mEffect.setParameter("tint", Color.MAGENTA);
				break;

			case R.id.vignette:
				mEffect = effectFactory.createEffect(EffectFactory.EFFECT_VIGNETTE);
				mEffect.setParameter("scale", .5f);
				break;

			default:
				break;
			}
		}

		private void applyEffect() {
			mEffect.apply(mTextures[0], mImageWidth, mImageHeight, mTextures[1]);
		}

		private void renderResult() {
			if (mCurrentEffect != R.id.none) {
				// if no effect is chosen, just render the original bitmap
				mTexRenderer.renderTexture(mTextures[1]);
			} else {
				// render the result of applyEffect()
				mTexRenderer.renderTexture(mTextures[0]);
			}
		}
	}
}
