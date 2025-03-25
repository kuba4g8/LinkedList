package linkedList;

import java.util.Random;
import java.util.Scanner;

public class LinkedListOperator {
    //wyprintowuje liste jednokierunkowa, mozna wybrac czy ma byc klikany enter czy nie
    public static <T> void printLinkedList(LinkedList<T> linkedListHead, boolean pressEnter)
    {
        LinkedListOperator.LinkedList<T> current = (LinkedList<T>) linkedListHead;
        int i = 1;

        if (linkedListHead == null)
        {
            main.println("Lista jest pusta :(");
            return;
        }

        do
        {
            if (pressEnter)
                System.out.println((i)+ " : " +current.item);
            else
                System.out.print((i)+ ": " +current.item + "; ");

            current = (LinkedList<T>) current.next;
            i++;
        } while (current != null && current != linkedListHead);
    }

    public static <T> void printLinkedList(LinkedTwoWayList<T> linkedListHead, boolean pressEnter) {
        if (linkedListHead == null) return; // Sprawdzenie pustej listy

        LinkedTwoWayList<T> current = linkedListHead;
        int i = 1;

        do {
            if (pressEnter)
                System.out.println(i + " : " + current.item);
            else
                System.out.print(i + ": " + current.item + "; ");

            current = current.next;
            i++;
        } while (current != linkedListHead); // Przerwij, gdy wrócisz do head
    }


    public static LinkedList<String> generateLinkedListWithInput(int ile, String inputText, boolean isCyclic)
    {
        Scanner scanner = new Scanner(System.in);

        LinkedList<String> head = null;
        LinkedList<String> prevItem = null;

        // Tworzenie listy
        for (int i = 0; i < ile; i++)
        {
            LinkedList<String> tempItem = new LinkedList<>();

            System.out.print(inputText);
            tempItem.item = scanner.nextLine();
            tempItem.next = null;

            if (head == null)
            {
                head = tempItem;
                prevItem = tempItem;
            }
            else
            {
                prevItem.next = tempItem;
                prevItem = tempItem;
            }
        }

        // Zamknięcie skanera
        scanner.close();

        // Jeśli lista ma być cykliczna
        if (isCyclic && prevItem != null)
        {
            prevItem.next = head;  // Ostatni element wskazuje na pierwszy element
        }
        else if (!isCyclic && prevItem != null)
        {
            prevItem.next = null;  // Ostatni element nie wskazuje na nic
        }

        return head;
    }


    //generowanie linkedListy jednokierunkowej losowymi wartosciami liczb calkowitych w zakresie od min do max. oraz jak duzo
    public static <T extends Integer> LinkedList<T> generateRandomNumOneWay(int howMuch, int minValue, int maxValue, boolean isCyclic)
    {
        LinkedList<T> head = null;
        LinkedList<T> prevItem = null;

        Random rand = new Random();

        for (int i = 0; i < howMuch; i++)
        {
            LinkedList<T> tempItem = new LinkedList<>();

            tempItem.item = (T) Integer.valueOf(rand.nextInt(minValue, maxValue)); //generowanie losowych liczb oraz przypisanie rzutowanie jawne do typu T z KLASY INTEGER. na incie nie dziala

            if (head == null)
            {
                head = tempItem;
                prevItem = tempItem;

                head.next = null;
            } // w przeciwnym razie prevItem.next przypisujemy na nowy oraz prevItem na nowy -> trace dostep do wczesniejszego ale moj wczesnijeszy to nowy essa?
            else
            {
                prevItem.next = tempItem;
                prevItem = tempItem;
            }

            //ostatni item to glowa
            if (i == howMuch - 1 && isCyclic)
            {
                tempItem.next = head;
            }
        }


        if (prevItem != null) {
            prevItem.next = head; //Ostatni beda pierwszymi czy cos
        }

        return head;
    }

    public static <T> LinkedList<T> generateLinkedListFromTab(T[] insertionTab) {
        LinkedList<T> head = null;
        LinkedList<T> prevItem = null;

        for (int i = 0; i < insertionTab.length; i++) {
            LinkedList<T> currentItem = new LinkedList<>();
            currentItem.item = insertionTab[i];

            if (head == null) {
                head = currentItem;
            } else {
                prevItem.next = currentItem;
            }

            prevItem = currentItem;
        }

        return head;
    }

    public static <T> LinkedList<T> deleteItemAtIndex(LinkedList<T> head, int index) {
        if (index == 0) {
            return head.next;
        }

        LinkedList<T> current = head;

        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }

        current.next = current.next.next;

        return head;
    }

    public static <T> LinkedTwoWayList<T> generateLinkedTwoWayList(T[] insertionTab, boolean isCyclic) {
        LinkedTwoWayList<T> head = null;
        LinkedTwoWayList<T> prevItem = null;

        for (int i = 0; i < insertionTab.length; i++) {
            LinkedTwoWayList<T> currentItem = new LinkedTwoWayList<>();
            currentItem.item = insertionTab[i];

            if (head == null) {
                head = currentItem;
                head.prev = head;
                head.next = head;
            } else {
                prevItem.next = currentItem;
                currentItem.prev = prevItem;
                currentItem.next = head;
                head.prev = currentItem;
            }

            prevItem = currentItem;
        }

        // Jeśli lista nie ma być cykliczna, ustawiamy prev ostatniego elementu na null
        if (!isCyclic && head != null && head.next != head) {
            head.prev = prevItem; // Poprawka: ustawiamy prev dla pierwszego elementu na ostatni
            prevItem.next = null;  // Ostatni element nie wskazuje na nic
            prevItem.prev = null;  // Ostatni element nie ma poprzednika
        }

        return head;
    }


    //usuwanie ostatniego Noda w liscie jednokierunkowej.
    public static <T> LinkedList<T> deleteLastElement(LinkedList<T> listToDelete) {
        if (listToDelete == null) return null;

        if (checkIfListCyclic(listToDelete)) {
            var current = listToDelete;
            while (current.next.next != listToDelete) {
                current = current.next;
            }
            current.next = listToDelete;
        } else {
            var current = listToDelete;
            while (current.next.next != null) {
                current = current.next;
            }
            current.next = null;
        }

        return listToDelete;
    }

    //usuwanie pierwszego elementu LinkedListy
    public static <T> LinkedList<T> deleteFirstElement(LinkedList<T> head) {
        if (head == null) return null;

        // ZMIANA: Obsługa listy cyklicznej
        if (checkIfListCyclic(head)) {
            if (head.next == head) { // Lista jednoelementowa
                return null;
            }

            //znajdowanie ostatniego elementu
            LinkedList<T> last = head;
            while (last.next != head) {
                last = last.next;
            }

            //Zaktualizowanie .next aby lista byla cykliczna
            last.next = head.next;
            return head.next; // Nowa głowa
        }

        // Domyślne zachowanie dla listy niecyklicznej
        return head.next;
    }

    // Dodanie elementu na ostatnim miejscu cyklicznej LinkedListy
    public static <T> LinkedList<T> linkedListInsertLast(LinkedList<T> head, T newItem) {
        LinkedList<T> newNode = new LinkedList<>();
        newNode.item = newItem;

        if (head == null) {
            newNode.next = newNode; // Cykliczna lista z jednym elementem
            return newNode;
        }

        LinkedList<T> current = head;
        while (current.next != head) {
            current = current.next;
        }

        current.next = newNode;
        newNode.next = head;

        return head;
    }

    // Dodanie elementu na poczatku cyklicznej LinkedListy
    public static <T> LinkedList<T> linkedListInsertFirst(LinkedList<T> head, T newItem) {
        LinkedList<T> newNode = new LinkedList<>();
        newNode.item = newItem;

        if (head == null) {
            return newNode; //zwraca newNode wskazujacy na nic, ma tylko item
        }

        LinkedList<T> current = head;

        if (head.next == head) {
            // Lista jest cykliczna, wstawiamy na początek
            newNode.next = head;
            current.next = newNode;  // Ostatni element wskazuje na nowy
            return newNode;  // Nowy element staje się head
        }

        // Lista nie jest cykliczna, przechodzimy przez listę, aby znaleźć ostatni element
        while (current.next != null) {
            current = current.next;
        }

        // Wstawiamy nowy element na początek
        newNode.next = head;
        current.next = newNode;

        return newNode;
    }


    //algorytm zolwia i zajaca
    private static <T> boolean checkIfListCyclic(LinkedList<T> cyclicList) {
        if (cyclicList == null) return false;

        LinkedList<T> slow = cyclicList;
        LinkedList<T> fast = cyclicList.next;

        while (fast != null && fast.next != null) {
            if (slow == fast) return true; // Wykryto cykl
            slow = slow.next;          // 1 krok
            fast = fast.next.next;     // 2 kroki
        }

        return false; // Fast doszedł do końca → brak cyklu
    }

    //LINKEDLISTY TWO WAY

    //algorytm zolwia i zajaca przeciazaony do TwoWay
    private static <T> boolean checkIfListCyclic(LinkedTwoWayList<T> cyclicList) {
        if (cyclicList == null) return false;

        LinkedTwoWayList<T> slow = cyclicList;
        LinkedTwoWayList<T> fast = cyclicList.next;

        while (fast != null && fast.next != null) {
            if (slow == fast)
                return true; // Wykryto cykl

            slow = slow.next;          // 1 krok
            fast = fast.next.next;     // 2 kroki
        }

        return false; // Fast doszedł do końca → brak cyklu
    }

    // Dodanie elementu na poczatek cyklicznej LinkedTwoWayList
    public static <T> LinkedTwoWayList<T> linkedListInsertFirst(LinkedTwoWayList<T> head, T newItem) {
        LinkedTwoWayList<T> newNode = new LinkedTwoWayList<>();
        newNode.item = newItem;

        if (head == null) {
            // Jeśli lista jest pusta, nowy węzeł wskazuje sam siebie
            newNode.next = newNode;
            newNode.prev = newNode;
            return newNode;
        }

        LinkedTwoWayList<T> tail = head.prev;  // Znajdujemy ostatni węzeł

        newNode.next = head;      // Nowy węzeł wskazuje na pierwszy
        newNode.prev = tail;      // Nowy węzeł wskazuje na ostatni
        head.prev = newNode;      // Stary pierwszy węzeł teraz wskazuje na nowy
        tail.next = newNode;      // Stary ostatni węzeł teraz wskazuje na nowy

        return newNode;  // Zwracamy nowy węzeł jako głowę
    }


    // Dodanie elementu na koniec cyklicznej LinkedTwoWayList
    public static <T> LinkedTwoWayList<T> linkedListInsertLast(LinkedTwoWayList<T> head, T item) {
        LinkedTwoWayList<T> newNode = new LinkedTwoWayList<>();
        newNode.item = item;

        if (head == null) {
            newNode.next = newNode;
            newNode.prev = newNode;
            return newNode;
        }

        LinkedTwoWayList<T> tail = head.prev;

        tail.next = newNode;
        newNode.prev = tail;
        newNode.next = head;
        head.prev = newNode;

        return head;
    }

    // Usuwanie elementu o podanym indeksie z LinkedTwoWayList
    public static <T> LinkedTwoWayList<T> deleteItemAtIndex(LinkedTwoWayList<T> head, int index) {
        if (head == null) // pusta lista
            return null;

        LinkedTwoWayList<T> current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;

            if (current == head) // Indeks poza zakresem
                return head;
        }

        if (current.next == current)
            return null;

        current.prev.next = current.next;
        current.next.prev = current.prev;

        if (current == head) head = current.next; // Aktualizacja head, jesli usuwamy pierwszy

        return head;
    }


    // Zwraca indeks szukajac po itemie
    public static Integer findIndexByNum(LinkedTwoWayList<Integer> head, int num) {
        if (head == null) {
            return -1;
        }

        // Sprawdzamy czy lista jest cykliczna
        boolean isCyclic = checkIfListCyclic(head);

        LinkedTwoWayList<Integer> current = head;
        int index = 0;
        LinkedTwoWayList<Integer> cycleStart = null;

        if (isCyclic) {
            // Dla listy cyklicznej - znajdujemy początek cyklu
            LinkedTwoWayList<Integer> slow = head;
            LinkedTwoWayList<Integer> fast = head;

            // Najpierw znajdujemy punkt spotkania
            do {
                slow = slow.next;
                fast = fast.next.next;
            } while (slow != fast);

            // Teraz znajdujemy początek cyklu
            slow = head;
            while (slow != fast) {
                slow = slow.next;
                fast = fast.next;
            }
            cycleStart = slow;
        }

        // Przeszukujemy listę
        while (current != null) {
            if (current.item == num) {
                return index;
            }

            current = current.next;
            index++;

            // Jeśli lista jest cykliczna i wróciliśmy do początku cyklu - przerywamy
            if (isCyclic && current == cycleStart) {
                break;
            }
        }

        return -1;
    }
    public static class LinkedList<T>
    {
        public T item;
        public LinkedList<T> next;


        //metoda length() obliczajaca dlugosc linkedListy
        public int length(LinkedTwoWayList<T> head) {
            int count = 0;
            LinkedList<T> current = head;

            while (current.next != null)
            {
                count++;
                current = current.next;
            }

            return count;
        }

        public void print(LinkedList<T> head, Boolean putEnter)
        {
            printLinkedList(head, putEnter);
        }

        public Integer findByItem(LinkedList<T> head, T item)
        {
            var current = head;
            Integer index = 0;

            while (current != null)
            {
                if (current.item.equals(item))
                {
                    return index;
                }
                index++;
                current = current.next;
            }
            return -1;
        }
    }

    public static class LinkedTwoWayList<T> extends  LinkedList<T>
    {
        public LinkedTwoWayList<T> next;
        public LinkedTwoWayList<T> prev;

        public int length(LinkedTwoWayList<T> head) {
            if (head == null) return 0; // Pusta lista

            int count = 0;
            LinkedTwoWayList<T> current = head;

            if (checkIfListCyclic(head)) {
                // do while -> poniewaz lista ma conajmniej jeden element. Iterujemy tak dlugo az current bedzie rowne head
                do {
                    count++;
                    current = current.next;
                } while (current.next != head);
            } else {
                // Jeśli lista jest niecykliczna, iteruj aż do null
                while (current != null) {
                    count++;
                    current = current.next;
                }
            }

            return count;
        }

    }
}
