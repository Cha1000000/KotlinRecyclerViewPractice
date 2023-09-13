# KotlinRecyclerViewPractice

Отработка задач с RecyclerView на практике.

Задание №1: 
Создать мини-приложение:
- один экран - список на RecyclerView из 10 айтемов, причем одновременно в экран влезают всего 3;
- в айтеме главное чтобы был 1 EditText;
- кнопка "собрать ввод";
  Задача - по клику на кнопку выводить алерт, где в тексте вывести ввод из каждого edittext с новой строки.
  Типа, "1. поле1
         2. поле2
         3. поле3"
  Задача* - реализовать множественное удаление. Спроектировать UX самостоятельно и реализовать, главное, чтобы можно было за раз удалить N элементов (хоть все).

Задание №2: 
Создать мини-приложение:
- один экран - список на RecyclerView из 10 айтемов;
- внутри айтема кнопка "refresh". Верстка любая. Например, можно выводить текст, куда по клику на кнопку вставлять рандомный UUID (UUID.randomUUID().toString()), и по умолчанию чтоб какой-то сгенерился до нажатия первого;
  Реализовать фичи:
- у каждого айтема кнопка удалить (или свайпом) - удаляет элемент из списка с анимацией (без алерта);
- по клику на refresh в айтеме меняется его содержимое, должна быть корректная анимация обновления айтема (не удаления/добавления, именно обновления содержимого);
- на экране должна быть FAB, по клику добавляем новый айтем со случайным содержимым;
- на экране должен быть реализован https://developer.android.com/develop/ui/views/touch-and-input/swipe/add-swipe-interface SwipeToRefresh, когда пользователь тянет палец вниз, должны создать полностью новый список рандомных айтемов.
