import * as THREE from 'three';

let torus, cube, cone;

export class SceneManager {
	constructor(scene) {
		const light = new THREE.DirectionalLight(0xffffff, 1);

		light.position.set(1, 1, 1);
		scene.add(light);

		const ambientLight = new THREE.AmbientLight(0x666666);
		scene.add(ambientLight);

		const grid = new THREE.GridHelper(10, 10);
		scene.add(grid);

		const axes = new THREE.AxesHelper(3);
		scene.add(axes);

		torus = new THREE.Mesh(
			new THREE.TorusGeometry(1, 0.4, 16, 100),
			new THREE.MeshPhongMaterial({ color: 0xff00ff })
		);
		torus.position.set(0, -3, 0);
		scene.add(torus);

		cone = new THREE.Mesh(new THREE.ConeGeometry(1, 2, 16, 1), new THREE.MeshPhongMaterial({ color: 0x00ffff }));
		cone.position.set(3, -3, 0);
		scene.add(cone);

		cube = new THREE.Mesh(new THREE.BoxGeometry(2, 2, 2), new THREE.MeshPhongMaterial({ color: 0xffff00 }));
		cube.rotation.set(0, Math.PI / 4, 0);
		cube.position.set(-3, -3, 0);
		scene.add(cube);

		// base grass
		let grass = new THREE.Mesh(new THREE.BoxGeometry(10, 1, 10), new THREE.MeshPhongMaterial({ color: 0x00ff00 }));
		grass.position.set(0, -0.5, 0);
		scene.add(grass);

		// castle base
		let castleBase = new THREE.Mesh(new THREE.BoxGeometry(2, 1, 2), new THREE.MeshPhongMaterial({ color: 0xee9955 }));
		castleBase.position.set(0, 0, -2);
		scene.add(castleBase);

		// caste tower
		let castleTower = new THREE.Mesh(new THREE.CylinderGeometry(0.3, 0.3, 1), new THREE.MeshPhongMaterial({ color: 0xee9955 }));
		castleTower.position.set(-1, 0 + 1 / 2, -1);
		scene.add(castleTower);

		// caste tower cap
		let castleTowerCap = new THREE.Mesh(new THREE.ConeGeometry(0.4, 0.5), new THREE.MeshPhongMaterial({ color: 0xee9955 }));
		castleTowerCap.position.set(-1, 1 + 0.5 / 2, -1);
		scene.add(castleTowerCap);

		// pond
		let pond = new THREE.Mesh(new THREE.CylinderGeometry(1, 1, 0.1), new THREE.MeshPhongMaterial({ color: 0x3355aa }));
		pond.position.set(0, 0, 2);
		scene.add(pond)
	}

	animate() {
		torus.rotation.x += 0.01;
		torus.rotation.y += 0.01;
	}
}
