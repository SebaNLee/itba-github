import request from 'supertest';
import { app } from '../app';
import { testDataSource } from './setup';

describe('Purchase API - Integration tests', () => {
  let jwt: string;

  beforeAll(async () => {
    // testDataSource initialized in setup
  });

  beforeEach(async () => {
    await testDataSource.getRepository('product').clear();
    await testDataSource.getRepository('category').clear();
    await testDataSource.getRepository('pantry').clear();
    await testDataSource.getRepository('list').clear();
    await testDataSource.getRepository('list_item').clear();
    await testDataSource.getRepository('purchase').clear();
    await testDataSource.getRepository('user').clear();
    await testDataSource.getRepository('user_verification_token').clear();

    const reg = await request(app).post('/api/users/register').send({ name: 'PInt', surname: 'Tester', email: 'pint@test.com', password: 'Pass123!' });
    const tokenRecord = await testDataSource.getRepository('user_verification_token').findOne({ where: { user: { id: reg.body.id } } });
    await request(app).post('/api/users/verify-account').send({ code: tokenRecord.token });
    const login = await request(app).post('/api/users/login').send({ email: 'pint@test.com', password: 'Pass123!' });
    jwt = login.body.token;
  });

  it('should create a purchase via API and restore it', async () => {
    const pantryRes = await request(app).post('/api/pantries').set('Authorization', `Bearer ${jwt}`).send({ name: 'P-Pantry' });
    const pantry = pantryRes.body || pantryRes.body.pantry || pantryRes.body;

    const catRes = await request(app).post('/api/categories').set('Authorization', `Bearer ${jwt}`).send({ name: 'P-Cat' });
    const category = catRes.body.category || catRes.body;

    const prodRes = await request(app).post('/api/products').set('Authorization', `Bearer ${jwt}`).send({ name: 'P-Prod', category: { id: category.id } });
    const product = prodRes.body.product || prodRes.body;

    const create = await request(app).post('/api/shopping-lists').set('Authorization', `Bearer ${jwt}`).send({ name: 'P-List', description: 'desc', recurring: false });
    expect(create.status).toBe(201);
    const list = create.body || create.body.list || create.body;

    const addItem = await request(app).post(`/api/shopping-lists/${list.id}/items`).set('Authorization', `Bearer ${jwt}`).send({ product: { id: product.id }, quantity: 2, unit: 'pcs' });
    expect(addItem.status).toBe(201);
    const item = addItem.body.item || addItem.body;

    const patch = await request(app).patch(`/api/shopping-lists/${list.id}/items/${item.id}`).set('Authorization', `Bearer ${jwt}`).send({ purchased: true });
    expect(patch.status).toBe(200);

    const purchase = await request(app).post(`/api/shopping-lists/${list.id}/purchase`).set('Authorization', `Bearer ${jwt}`).send({ metadata: { note: 'bought' } });
    expect(purchase.status).toBe(201);
    const purchased = purchase.body || purchase.body.purchase || purchase.body;

    const getOne = await request(app).get(`/api/purchases/${purchased.id}`).set('Authorization', `Bearer ${jwt}`);
    expect(getOne.status).toBe(200);

    const restore = await request(app).post(`/api/purchases/${purchased.id}/restore`).set('Authorization', `Bearer ${jwt}`);
    expect(restore.status).toBe(201);
    const restored = restore.body || restore.body.list || restore.body;
    expect(restored).toBeDefined();
  });
});
