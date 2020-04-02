package com.androiddevs.augmentedfaces

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.ar.core.AugmentedFace
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.rendering.Texture
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.AugmentedFaceNode
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.CompletableFuture

class MainActivity : AppCompatActivity() {

    private var faceRenderable: ModelRenderable? = null
    private var faceTexture: Texture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadModel()
    }

    private fun loadModel() {
        val modelRenderable = ModelRenderable.builder()
            .setSource(this, R.raw.fox_face)
            .build()
        val texture = Texture.builder()
            .setSource(this, R.drawable.clown_face_mesh_texture)
            .build()
        CompletableFuture.allOf(modelRenderable, texture)
            .thenAccept {
                faceRenderable = modelRenderable.get().apply {
                    isShadowCaster = false
                    isShadowReceiver = false
                }
                faceTexture = texture.get()
            }.exceptionally {
                Log.e("MainActivity", "ERROR: $it")
                Toast.makeText(this, "Error loading model: $it", Toast.LENGTH_LONG).show()
                null
            }
    }
}
