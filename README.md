# DataStucture_HW3

- In add product- if the productID already exists we assume it's intential inorsder rto replace the product in the system.
- The products order is not importent so binary tree is not benefitial, 
we do need to get an instance in no time, so hashmap is doing it in O(1)
since it performing a function on the productID and get the instnce of Product
- the productID is not part of the instance since it's the key fo rthe hashmap function, so no need to save it twice/
- the hashmap function does not allocate a fixed amount of space upfront, once it reach 75% capacity it's rezise it!

- for Orders management we do allocate map for all products in order, each first allocation is 16 places, but to be on the safe side which is the worst case, every order can have M items. 
- We oveloading the checkAvailability function so the reduceQuantity checkes Availability by sending pointer of Product.
- The orders saved in hashMap and the orderID we decided that it will be a following number O(1), since we dont want to check for every order which key is available O(n), .