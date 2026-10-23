from pathlib import Path

p = Path("SmartPantryManager/app/src/main/java/com/teybrag/smartpantry/DatabaseHelper.java")
s = p.read_text()
s = s.replace("private static final int DB_VERSION = 1;", "private static final int DB_VERSION = 2;")
s = s.replace('        seedRecipes(db);', '        seedRecipes(db);\n        seedAdditionalRecipes(db);')

start = s.index("@Override public void onUpgrade(")
end = s.index("\n\n    public long addPantryItem", start)
s = s[:start] + """@Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Preserve saved pantry items and add new recipes without destructive resets.
        seedAdditionalRecipes(db);
    }""" + s[end:]

marker = "    private static void seedRecipes(SQLiteDatabase db) {"
addition = r"""
    private static void seedAdditionalRecipes(SQLiteDatabase db) {
        addRecipeIfMissing(db, "Egg and Tomato Scramble",
            "1. Beat the eggs. 2. Chop the tomato. 3. Cook together until eggs are fully set.",
            new Object[][]{{"eggs",2,"pcs"},{"tomato",1,"pcs"}});
        addRecipeIfMissing(db, "Cheese and Tomato Toast",
            "1. Put cheese and sliced tomato on bread. 2. Toast until warm and crisp.",
            new Object[][]{{"bread",2,"slices"},{"cheese",30,"g"},{"tomato",1,"pcs"}});
        addRecipeIfMissing(db, "Banana Egg Pancakes",
            "1. Mash one banana. 2. Mix with beaten eggs. 3. Cook small pancakes until set on both sides.",
            new Object[][]{{"banana",1,"pcs"},{"eggs",2,"pcs"}});
        addRecipeIfMissing(db, "Potato and Egg Skillet",
            "1. Dice and boil potatoes until nearly tender. 2. Add eggs and cook until fully set.",
            new Object[][]{{"potatoes",2,"pcs"},{"eggs",2,"pcs"}});
        addRecipeIfMissing(db, "Tomato and Onion Salad",
            "1. Wash and chop tomatoes and onion. 2. Mix and serve fresh.",
            new Object[][]{{"tomatoes",2,"pcs"},{"onion",1,"pcs"}});
        addRecipeIfMissing(db, "Simple Tomato Garlic Pasta",
            "1. Boil pasta. 2. Cook chopped tomatoes and garlic. 3. Mix with drained pasta.",
            new Object[][]{{"pasta",200,"g"},{"tomatoes",2,"pcs"},{"garlic",2,"cloves"}});
        addRecipeIfMissing(db, "Banana Oats",
            "1. Put oats in a bowl. 2. Add milk and sliced banana. 3. Serve warm or chilled.",
            new Object[][]{{"oats",50,"g"},{"banana",1,"pcs"},{"milk",100,"ml"}});
        addRecipeIfMissing(db, "Chicken and Potato Bowl",
            "1. Cut chicken into small pieces and cook thoroughly. 2. Boil or roast potatoes until tender. 3. Serve together.",
            new Object[][]{{"chicken",200,"g"},{"potatoes",2,"pcs"}});
        addRecipeIfMissing(db, "Apple Banana Fruit Bowl",
            "1. Wash and chop the apple and banana. 2. Mix and serve.",
            new Object[][]{{"apple",1,"pcs"},{"banana",1,"pcs"}});
        addRecipeIfMissing(db, "Spinach Egg Scramble",
            "1. Wash and chop spinach. 2. Cook until wilted. 3. Add eggs and cook until set.",
            new Object[][]{{"spinach",50,"g"},{"eggs",2,"pcs"}});
        addRecipeIfMissing(db, "Cheese Omelette",
            "1. Beat the eggs. 2. Cook in a non-stick pan. 3. Add cheese and fold when set.",
            new Object[][]{{"eggs",2,"pcs"},{"cheese",30,"g"}});
        addRecipeIfMissing(db, "Peanut Butter Toast",
            "1. Toast the bread. 2. Spread peanut butter over toast and serve.",
            new Object[][]{{"bread",2,"slices"},{"peanut butter",20,"g"}});
        addRecipeIfMissing(db, "Rice and Egg Bowl",
            "1. Cook rice according to its package instructions. 2. Scramble eggs until fully cooked. 3. Serve together.",
            new Object[][]{{"rice",100,"g"},{"eggs",2,"pcs"}});
        addRecipeIfMissing(db, "Carrot Potato Mash",
            "1. Chop carrots and potatoes. 2. Boil until soft. 3. Mash together.",
            new Object[][]{{"carrots",2,"pcs"},{"potatoes",2,"pcs"}});
    }

    private static void addRecipeIfMissing(SQLiteDatabase db, String name, String steps, Object[][] ingredients) {
        ContentValues rv = new ContentValues();
        rv.put("name", name);
        rv.put("steps", steps);
        long recipeId = db.insertWithOnConflict("recipes", null, rv, SQLiteDatabase.CONFLICT_IGNORE);
        if (recipeId == -1) return;
        for (Object[] ingredient : ingredients) {
            ContentValues iv = new ContentValues();
            iv.put("recipe_id", recipeId);
            iv.put("name", (String) ingredient[0]);
            iv.put("quantity", ((Number) ingredient[1]).doubleValue());
            iv.put("unit", (String) ingredient[2]);
            db.insert("recipe_ingredients", null, iv);
        }
    }

"""
if marker not in s:
    raise SystemExit("Could not locate recipe seeding method")
s = s.replace(marker, addition + marker)
p.write_text(s)
