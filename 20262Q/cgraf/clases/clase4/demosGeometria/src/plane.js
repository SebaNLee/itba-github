// @ts-nocheck
import * as THREE from 'three';

function buildPlane(buffers, width, height, widthSegments, heightSegments) {
	let positions = buffers.positions;
	let indices = buffers.indices;
	let normals = buffers.normals;
	let uvs = buffers.uvs;

    for (let i = 0; i < widthSegments; i++) {
        for (let j = 0; j < heightSegments; j++) {
            // tomo:
            // width = x
            // height = z
         
            const x = i * (width / widthSegments) - width / 2;
            const z = j * (height / heightSegments) - height / 2;

            const u = i / heightSegments;
            const v = j / widthSegments;

            positions.push(x, 0, z);
            normals.push(0, 1, 0);
            uvs.push(u, v);

            // ad
            // bc
            const a = i * (widthSegments + 1) + j;
            const b = a + widthSegments + 1;
            const c = b + 1;
            const d = a + 1;

            indices.push(a, b, d);
            indices.push(b, c, d);
        }
    }
}


export function createPlane(width, height, widthSegments, heightSegments) {
    let geometry = new THREE.BufferGeometry();

	const positions = [];
	const indices = [];
	const normals = [];
	const uvs = [];

    buildPlane({ positions, indices, normals, uvs }, width, height, widthSegments, heightSegments);

    geometry.setAttribute('position', new THREE.Float32BufferAttribute(positions, 3));
    geometry.setAttribute('normal', new THREE.Float32BufferAttribute(normals, 3));
    geometry.setAttribute('uv', new THREE.Float32BufferAttribute(uvs, 2));
    geometry.setIndex(indices);

    return geometry;
}
