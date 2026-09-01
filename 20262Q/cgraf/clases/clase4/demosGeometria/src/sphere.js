import * as THREE from 'three';

function buildSphere(buffers, radius, radialSegments, heightSegments) {
    const angleStep = (2 * Math.PI) / radialSegments;
	const heightStep = radius / heightSegments;

	let positions = buffers.positions;
	let indices = buffers.indices;
	let normals = buffers.normals;
	let uvs = buffers.uvs;

    // TODO

    // upper sphere
    for (let i = 0; i < heightSegments / 2; i++) {
        const y = i * heightStep;
        const v = i / heightSegments;

		for (let j = 0; j <= radialSegments; j++) {
			const angle = j * angleStep;
			const x = (1 - v) * radius * Math.cos(angle);
			const z = (1 - v) * radius * Math.sin(angle);
			const u = j / radialSegments;
			
            positions.push(x, y, z);
			normals.push(x, 0, z);
			uvs.push(u, v);

            if (i < heightSegments / 2 && j < radialSegments / 2) {
                const a = i * (radialSegments + 1) + j;
                const b = a + radialSegments + 1;
                const c = a + radialSegments + 2;
                const d = a + 1;

                indices.push(a, b, d);
                indices.push(b, c, d);
            }
        }
    }


    // lower sphere
    for (let i = 0; i > - heightSegments / 2; i--) {
        const y = i * heightStep;
        const v = i / heightSegments;

		for (let j = 0; j <= radialSegments; j++) {
			const angle = j * angleStep;
			const x = v * radius * Math.cos(angle);
			const z = v * radius * Math.sin(angle);
            const u = j / radialSegments;
			
            positions.push(x, y, z);
			normals.push(x, 0, z);
			uvs.push(u, v);

            if (i < heightSegments && j < radialSegments) {
                const a = i * (radialSegments + 1) + j;
                const b = a + radialSegments + 1;
                const c = a + radialSegments + 2;
                const d = a + 1;

                indices.push(a, b, d);
                indices.push(b, c, d);
            }
        }
    }
}

export function createSphere(radius, radialSegments, heightSegments) {
    let geometry = new THREE.BufferGeometry();

    const positions = [];
    const indices = [];
    const normals = [];
    const uvs = [];

    buildSphere({ positions, indices, normals, uvs }, radius, radialSegments, heightSegments);

    geometry.setAttribute('position', new THREE.Float32BufferAttribute(positions, 3));
    geometry.setAttribute('normal', new THREE.Float32BufferAttribute(normals, 3));
    geometry.setAttribute('uv', new THREE.Float32BufferAttribute(uvs, 2));
    geometry.setIndex(indices);

    return geometry;
}