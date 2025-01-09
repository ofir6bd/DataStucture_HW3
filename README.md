# DataStucture_HW3

- The products order is not importent so binary tree is not benefitial, 
we do need to get an instance in no time, so hashmap is doing it in O(1)
since it performing a function on the productID and get the instnce of Product
- the productID is not part of the instance since it's the key fo rthe hashmap function, so no need to save it twice/
- the hashmap function does not allocate a fixed amount of space upfrnt, once it reach 75% capacity it's rezise it!

- for Orders management I do allocate map for all products in order, each first allocation is 16 places, but to be on the safe side which is the worst case, every order can have M items. 
- We oveloading the checkAvailability function so the reduceQuantity checkes Availability by sending pointer of Product.
