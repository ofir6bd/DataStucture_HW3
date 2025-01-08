# DataStucture_HW3

- The products order is not importent so binary tree is not benefitial, 
we do need to get an instance in no time, so hashmap is doing it in O(1)
since it performing a function on the productID and get the instnce of Product
- the productID is not part of the instance since it's the key fo rthe hashmap function, so no need to save it twice/
- the hashmap function does not allocate a fixed amount of space upfrnt, once it reach 75% capacity it's rezise it!
