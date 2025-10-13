import { Router } from 'express';
import {
    getLists, getListById, registerList, updateList, deleteList, purchaseShoppingList,
    resetShoppingList, moveToPantry, shareShoppingList, sharedUsersShoppingList, revokeShareShoppingList
} from '../controllers/list.controller';
import { authenticateJWT } from '../middleware/authToken';

const router = Router();

/**
 * @swagger
 * /api/shopping-lists:
 *   post:
 *     security:
 *       - bearerAuth: []
 *     summary: Create new shopping list
 *     tags: [Shopping Lists]
 *     operationId: createShoppingList
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/definitions/ShoppingListCreate'
 *     responses:
 *       201:
 *         description: Shopping list successfully created
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/definitions/ShoppingList'
 *       400:
 *         $ref: '#/responses/BadRequest'
 *       401:
 *         $ref: '#/responses/Unauthorized'
 *       500:
 *         $ref: '#/responses/ServerError'
 */
router.post('/', authenticateJWT, registerList);

/**
 * @swagger
 * /api/shopping-lists:
 *   get:
 *     security:
 *       - bearerAuth: []
 *     summary: Get shopping lists
 *     tags: [Shopping Lists]
 *     operationId: getAllShoppingLists
 *     parameters:
 *       - in: query
 *         name: name
 *         schema:
 *           type: string
 *         description: Shopping list name
 *       - in: query
 *         name: owner
 *         schema:
 *           type: boolean
 *         description: Filter by ownership (true=me, false=others, unspecified=any)
 *       - in: query
 *         name: recurring
 *         schema:
 *           type: boolean
 *         description: Filter recurring lists (true=recurring, false=not recurring, unspecified=any)
 *       - in: query
 *         name: page
 *         schema:
 *           type: integer
 *           default: 1
 *         description: Page number
 *       - in: query
 *         name: per_page
 *         schema:
 *           type: integer
 *           default: 10
 *         description: Results per page
 *       - in: query
 *         name: sort_by
 *         schema:
 *           type: string
 *           enum: [name, owner, createdAt, updatedAt, lastPurchasedAt]
 *           default: name
 *         description: Sort field
 *       - in: query
 *         name: order
 *         schema:
 *           type: string
 *           enum: [ASC, DESC]
 *           default: ASC
 *         description: Sort order
 *     responses:
 *       200:
 *         description: List of shopping lists
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               $ref: '#/definitions/ShoppingListsArray'
 *       400:
 *         $ref: '#/responses/BadRequest'
 *       401:
 *         $ref: '#/responses/Unauthorized'
 *       500:
 *         $ref: '#/responses/ServerError'
 */
router.get('/', authenticateJWT, getLists);

/**
 * @swagger
 * /api/shopping-lists/{id}:
 *   get:
 *     security:
 *       - bearerAuth: []
 *     summary: Get shopping list
 *     tags: [Shopping Lists]
 *     operationId: getShoppingListById
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: integer
 *         description: Shopping list ID
 *     responses:
 *       200:
 *         description: Shopping list data
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/definitions/ShoppingList'
 *       400:
 *         $ref: '#/responses/BadRequest'
 *       401:
 *         $ref: '#/responses/Unauthorized'
 *       404:
 *         $ref: '#/responses/NotFound'
 *       500:
 *         $ref: '#/responses/ServerError'
 */
router.get('/:id', authenticateJWT, getListById);

/**
 * @swagger
 * /api/shopping-lists/{id}:
 *   put:
 *     security:
 *       - bearerAuth: []
 *     summary: Update shopping list
 *     tags: [Shopping Lists]
 *     operationId: updateShoppingList
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: integer
 *         description: Shopping list ID
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/definitions/ShoppingListUpdate'
 *     responses:
 *       200:
 *         description: Shopping list successfully updated
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/definitions/ShoppingList'
 *       400:
 *         $ref: '#/responses/BadRequest'
 *       401:
 *         $ref: '#/responses/Unauthorized'
 *       404:
 *         $ref: '#/responses/NotFound'
 *       500:
 *         $ref: '#/responses/ServerError'
 */
router.put('/:id', authenticateJWT, updateList);

/**
 * @swagger
 * /api/shopping-lists/{id}:
 *   delete:
 *     security:
 *       - bearerAuth: []
 *     summary: Delete shopping list
 *     tags: [Shopping Lists]
 *     operationId: deleteShoppingList
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: integer
 *         description: Shopping list ID
 *     responses:
 *       200:
 *         description: Shopping list successfully deleted
 *       400:
 *         $ref: '#/responses/BadRequest'
 *       401:
 *         $ref: '#/responses/Unauthorized'
 *       404:
 *         $ref: '#/responses/NotFound'
 *       500:
 *         $ref: '#/responses/ServerError'
 */
router.delete('/:id', authenticateJWT, deleteList);

/**
 * @swagger
 * /api/shopping-lists/{id}/purchase:
 *   post:
 *     security:
 *       - bearerAuth: []
 *     summary: Purchase shopping list
 *     tags: [Shopping Lists]
 *     operationId: purchaseShoppingList
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: integer
 *         description: Shopping list ID
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               metadata:
 *                 type: object
 *                 description: Optional metadata for the purchase
 *     responses:
 *       201:
 *         description: Shopping list marked as purchased and history purchase entry created.
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/definitions/ShoppingList'
 *       400:
 *         $ref: '#/responses/BadRequest'
 *       401:
 *         $ref: '#/responses/Unauthorized'
 *       404:
 *         $ref: '#/responses/NotFound'
 *       500:
 *         $ref: '#/responses/ServerError'
 */
router.post('/:id/purchase', authenticateJWT, purchaseShoppingList);

/**
 * @swagger
 * /api/shopping-lists/{id}/reset:
 *   post:
 *     security:
 *       - bearerAuth: []
 *     summary: Reset shopping list
 *     tags: [Shopping Lists]
 *     operationId: resetShoppingList
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: integer
 *         description: Shopping list ID
 *     responses:
 *       200:
 *         description: Shopping list successfully reseted
 *         content:
 *           application/json:
 *             schema:
 *                $ref: '#/definitions/ListItemArray'
 *       400:
 *         $ref: '#/responses/BadRequest'
 *       401:
 *         $ref: '#/responses/Unauthorized'
 *       404:
 *         $ref: '#/responses/NotFound'
 *       500:
 *         $ref: '#/responses/ServerError'
 */
router.post('/:id/reset', authenticateJWT, resetShoppingList);

/**
 * @swagger
 * /api/shopping-lists/{id}/move-to-pantry:
 *   post:
 *     security:
 *       - bearerAuth: []
 *     summary: Move items to pantry
 *     tags: [Shopping Lists]
 *     operationId: moveToPantry
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: integer
 *         description: Shopping list ID
 *     responses:
 *       200:
 *         description: Items successfully moved to pantry
 *       400:
 *         $ref: '#/responses/BadRequest'
 *       401:
 *         $ref: '#/responses/Unauthorized'
 *       404:
 *         $ref: '#/responses/NotFound'
 *       500:
 *         $ref: '#/responses/ServerError'
 */
router.post('/:id/move-to-pantry', authenticateJWT, moveToPantry);

/**
 * @swagger
 * /api/shopping-lists/{id}/share:
 *   post:
 *     security:
 *       - bearerAuth: []
 *     summary: Share shopping list
 *     tags: [Shopping Lists]
 *     operationId: shareShoppingList
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: integer
 *         description: Shopping list ID
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               email:
 *                 type: string
 *                 format: email
 *                 description: User email
 *     responses:
 *       200:
 *         description: Shopping list successfully shared
 *       400:
 *         $ref: '#/responses/BadRequest'
 *       401:
 *         $ref: '#/responses/Unauthorized'
 *       404:
 *         $ref: '#/responses/NotFound'
 *       500:
 *         $ref: '#/responses/ServerError'
 */
router.post('/:id/share', authenticateJWT, shareShoppingList);

/**
 * @swagger
 * /api/shopping-lists/{id}/shared-users:
 *   get:
 *     security:
 *       - bearerAuth: []
 *     summary: Get shared users
 *     tags: [Shopping Lists]
 *     operationId: sharedUsersShoppingList
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: integer
 *         description: Shopping list ID
 *     responses:
 *       200:
 *         description: List of users with access to the shared list
 *         content:
 *           application/json:
 *             schema:
 *               type: array
 *               items:
 *                 type: object
 *                 properties:
 *                   id:
 *                     type: integer
 *                   name:
 *                     type: string
 *                   surname:
 *                     type: string
 *                   email:
 *                     type: string
 *                     format: email
 *       400:
 *         $ref: '#/responses/BadRequest'
 *       401:
 *         $ref: '#/responses/Unauthorized'
 *       404:
 *         $ref: '#/responses/NotFound'
 *       500:
 *         $ref: '#/responses/ServerError'
 */
router.get('/:id/shared-users', authenticateJWT, sharedUsersShoppingList);

/**
 * @swagger
 * /api/shopping-lists/{id}/share/{user_id}:
 *   delete:
 *     security:
 *       - bearerAuth: []
 *     summary: Revoke shopping list access
 *     tags: [Shopping Lists]
 *     operationId: revokeShareShoppingList
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: integer
 *         description: Shopping list ID
 *       - in: path
 *         name: user_id
 *         required: true
 *         schema:
 *           type: integer
 *         description: User ID
 *     responses:
 *       200:
 *         description: User access successfully revoked
 *       400:
 *         $ref: '#/responses/BadRequest'
 *       401:
 *         $ref: '#/responses/Unauthorized'
 *       404:
 *         $ref: '#/responses/NotFound'
 *       500:
 *         $ref: '#/responses/ServerError'
 */
router.delete('/:id/share/:user_id', authenticateJWT, revokeShareShoppingList);

export default router;