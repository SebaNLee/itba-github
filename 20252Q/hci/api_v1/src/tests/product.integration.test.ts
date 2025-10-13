import request from 'supertest';
import { app } from '../app';
import { testDataSource } from './setup';

describe('Product API - Integration tests', () => {
  let jwt: string;

  beforeAll(async () => {
    // testDataSource initialized in setup
  });

  beforeEach(async () => {
    await testDataSource.getRepository('product').clear();
    await testDataSource.getRepository('category').clear();
    await testDataSource.getRepository('pantry').clear();
    await testDataSource.getRepository('user').clear();
    await testDataSource.getRepository('user_verification_token').clear();

    const reg = await request(app).post('/api/users/register').send({ name: 'ProdInt', surname: 'Tester', email: 'prodint@test.com', password: 'Pass123!' });
    const tokenRecord = await testDataSource.getRepository('user_verification_token').findOne({ where: { user: { id: reg.body.id } } });
    await request(app).post('/api/users/verify-account').send({ code: tokenRecord.token });
    const login = await request(app).post('/api/users/login').send({ email: 'prodint@test.com', password: 'Pass123!' });
    jwt = login.body.token;
  });

  it('should create a product', async () => {
    const cat = await request(app).post('/api/categories').set('Authorization', `Bearer ${jwt}`).send({ name: 'ProdCat' });
    const createdCat = cat.body.category || cat.body;
    const pantry = await request(app).post('/api/pantries').set('Authorization', `Bearer ${jwt}`).send({ name: 'ProdPantry' });
    const createdPantry = pantry.body || pantry.body;

    const res = await request(app)
      .post('/api/products')
      .set('Authorization', `Bearer ${jwt}`)
      .send({ name: 'IntegrationProduct', category: { id: createdCat.id } });

    expect(res.status).toBe(201);
    expect(res.body).toBeDefined();
    expect(res.body.name || res.body.product?.name).toBeDefined();
  });

  it('should list and filter products', async () => {
    const cat = await request(app).post('/api/categories').set('Authorization', `Bearer ${jwt}`).send({ name: 'FilterCat' });
    const createdCat = cat.body.category || cat.body;
    const pantry = await request(app).post('/api/pantries').set('Authorization', `Bearer ${jwt}`).send({ name: 'FilterPantry' });
    const createdPantry = pantry.body || pantry.body;

    await request(app).post('/api/products').set('Authorization', `Bearer ${jwt}`).send({ name: 'FP1', category: { id: createdCat.id } });
    await request(app).post('/api/products').set('Authorization', `Bearer ${jwt}`).send({ name: 'FP2', category: { id: createdCat.id } });

    const all = await request(app).get('/api/products').set('Authorization', `Bearer ${jwt}`);
    expect(all.status).toBe(200);
    expect(Array.isArray(all.body.products || all.body)).toBe(true);

    const byCategory = await request(app).get(`/api/products?category_id=${createdCat.id}`).set('Authorization', `Bearer ${jwt}`);
    expect(byCategory.status).toBe(200);
    const listByCat = byCategory.body.products || byCategory.body;
    expect(listByCat.length).toBeGreaterThanOrEqual(2);

    const byPantry = await request(app).get(`/api/products?pantry_id=${createdPantry.id}`).set('Authorization', `Bearer ${jwt}`);
    expect(byPantry.status).toBe(200);
    const listByPantry = byPantry.body.products || byPantry.body;
    expect(listByPantry.length).toBeGreaterThanOrEqual(1);
  });

  it('should get, update and delete a product', async () => {
    const cat = await request(app).post('/api/categories').set('Authorization', `Bearer ${jwt}`).send({ name: 'ManageCat' });
    const createdCat = cat.body.category || cat.body;
    const pantry = await request(app).post('/api/pantries').set('Authorization', `Bearer ${jwt}`).send({ name: 'ManagePantry' });
    const createdPantry = pantry.body || pantry.body;

    const create = await request(app).post('/api/products').set('Authorization', `Bearer ${jwt}`).send({ name: 'ToManage', category_id: createdCat.id, pantry_id: createdPantry.id });
    const prod = create.body.product || create.body;
    const id = prod.id;

    const getRes = await request(app).get(`/api/products/${id}`).set('Authorization', `Bearer ${jwt}`);
    expect(getRes.status).toBe(200);

    const updateRes = await request(app).put(`/api/products/${id}`).set('Authorization', `Bearer ${jwt}`).send({ name: 'UpdatedProd' });
    expect(updateRes.status).toBe(200);
    expect(updateRes.body.product || updateRes.body.name).toBeDefined();

    const deleteRes = await request(app).delete(`/api/products/${id}`).set('Authorization', `Bearer ${jwt}`);
    expect(deleteRes.status).toBe(200);
  });
});
